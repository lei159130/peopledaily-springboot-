package com.lee1314.peopledaily.commons.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lee1314.peopledaily.entity.PeopleDaily;
import com.lee1314.peopledaily.service.PeopleDailyService;
import com.lee1314.peopledaily.service.ProxyInfoService;
import com.lee1314.peopledaily.service.SysConfigService;
import com.lee1314.peopledaily.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.jms.Queue;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author leili
 */
@Slf4j
@Component
public class QueueConsumerListener {

    private static final int RETRY = 5;
    private static final String[] IGNORE_PROPERTIES = new String[]{"id", "title", "rongyunId", "uniqueId", "audioUrl",
            "audioPlayTime", "shareUrl", "shareImage", "newsTime"};

    @Resource(name = "save-queue")
    private Queue queue;

    private final OkHttpUtil okHttpUtil;
    private final SysConfigService sysConfigService;
    private final ProxyInfoService proxyInfoService;
    private final PeopleDailyService peopleDailyService;
    private final JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    public QueueConsumerListener(OkHttpUtil okHttpUtil, SysConfigService sysConfigService, ProxyInfoService proxyInfoService, PeopleDailyService peopleDailyService, JmsMessagingTemplate jmsMessagingTemplate) {
        this.okHttpUtil = okHttpUtil;
        this.sysConfigService = sysConfigService;
        this.proxyInfoService = proxyInfoService;
        this.peopleDailyService = peopleDailyService;
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    /**
     * 获取详情页数据
     *
     * @param message
     */
    @JmsListener(destination = "${spring.activemq.reptile-queue}", containerFactory = "queueListener")
    public void reptileQueue(String message) {
        log.info("reptile message:{}", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        PeopleDaily entity = JSON.parseObject(message, PeopleDaily.class);
        if (entity == null) {
            return;
        }
        TreeMap<String, String> params = new TreeMap<>();
        params.put("id", String.valueOf(entity.getId()));
        params.put("interface_code", "610");

        StringBuilder sb = new StringBuilder();
        String url = sysConfigService.findByBusinessAndCode("peopledaily", "getInfoUp").getContent();
        for (Map.Entry<String, String> param : params.entrySet()) {
            url = url.replace(param.getKey().toUpperCase(), param.getValue());
            sb.append(param.getKey());
            sb.append("=");
            sb.append(param.getValue());
            sb.append("|");
        }
        sb.deleteCharAt(sb.length() - 1);

        //生成加密KEY
        String key = sysConfigService.findByBusinessAndCode("peopledaily", "securitykey").getContent();
        sb.append(key);
        url = url.replace("securitykey".toUpperCase(), DigestUtils.md5DigestAsHex(sb.toString().getBytes()));

        for (int count = 1; count < RETRY; count++) {
            String result = okHttpUtil.get(url);
            if (StringUtils.isEmpty(result)) {
                try {
                    Thread.sleep(1000);
                    proxyInfoService.refresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            JSONObject json = JSON.parseObject(result);
            if (!"0".equals(json.getJSONObject("result").getString("errorCode"))) {
                try {
                    Thread.sleep(1000);
                    proxyInfoService.refresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            PeopleDaily d = json.getObject("data", PeopleDaily.class);
            BeanUtils.copyProperties(d, entity, IGNORE_PROPERTIES);
            break;
        }
        String contents = entity.getContents();
        contents = contents.replace("&lt;", "<");
        contents = contents.replace("&gt;", ">");
        entity.setContents(contents);

        jmsMessagingTemplate.convertAndSend(queue, JSON.toJSONString(entity));
    }

    /**
     * 插入数据
     *
     * @param message
     */
    @JmsListener(destination = "${spring.activemq.save-queue}", containerFactory = "queueListener")
    public void saveQueue(String message) {
        log.info("save message:{}", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        PeopleDaily entity = JSON.parseObject(message, PeopleDaily.class);
        if (entity == null) {
            return;
        }
        peopleDailyService.insert(entity);
    }
}
