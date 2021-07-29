package com.lee1314.peopledaily.reptile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lee1314.peopledaily.entity.PeopleDaily;
import com.lee1314.peopledaily.service.ProxyInfoService;
import com.lee1314.peopledaily.service.SysConfigService;
import com.lee1314.peopledaily.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.jms.Queue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Component
public class PeopleDailyReptile {
    private static final int RETRY = 5;

    @Resource(name = "reptile-queue")
    private Queue queue;

    private final OkHttpUtil okHttpUtil;
    private final RedisTemplate redisTemplate;
    private final ProxyInfoService proxyInfoService;
    private final SysConfigService sysConfigService;
    private final JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    public PeopleDailyReptile(OkHttpUtil okHttpUtil, RedisTemplate redisTemplate, ProxyInfoService proxyInfoService, SysConfigService sysConfigService, JmsMessagingTemplate jmsMessagingTemplate) {
        this.okHttpUtil = okHttpUtil;
        this.redisTemplate = redisTemplate;
        this.proxyInfoService = proxyInfoService;
        this.sysConfigService = sysConfigService;
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    /**
     * 查询多条数据
     *
     * @param seminarId
     */
    public void queryLists(String seminarId) {
        boolean havaMore = true;
        for (int page = 1; havaMore; page++) {
            TreeMap<String, String> params = new TreeMap<>();
            params.put("seminar_id", seminarId);
            params.put("refresh_time", "0");
            params.put("page", String.valueOf(page));
            params.put("interface_code", "610");

            StringBuilder sb = new StringBuilder();
            String url = sysConfigService.findByBusinessAndCode("peopledaily", "getSeminarList").getContent();
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

            List<PeopleDaily> dtos = new ArrayList<>();
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
                JSONObject res = json.getJSONObject("result");
                if (!"0".equals(res.getString("errorCode"))) {
                    try {
                        Thread.sleep(1000);
                        proxyInfoService.refresh();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                havaMore = res.getBoolean("have_more");

                JSONObject data = json.getJSONObject("data");
                dtos.addAll(data.getJSONArray("plate").toJavaList(PeopleDaily.class));
                break;
            }

            for (PeopleDaily entity : dtos) {
                if (redisTemplate.opsForSet().isMember("collection:ids:" + seminarId, entity.getId())) {
                    return;
                }
                log.info("put queue:{}", entity);
                String redisKey = "collection:ids:" + seminarId;
                redisTemplate.opsForSet().add(redisKey, entity.getId());
                jmsMessagingTemplate.convertAndSend(queue, JSON.toJSONString(entity));
            }
        }
    }

}
