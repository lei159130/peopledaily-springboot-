package com.lee1314.peopledaily.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lee1314.peopledaily.entity.SysConfig;
import com.lee1314.peopledaily.entity.dto.ProxyInfoDto;
import com.lee1314.peopledaily.service.ProxyInfoService;
import com.lee1314.peopledaily.service.SysConfigService;
import com.lee1314.peopledaily.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leili
 */
@Slf4j
@Service
public class ProxyInfoServiceImpl implements ProxyInfoService {

    private static final Object lock = new Object();

    private OkHttpUtil okHttpUtil;
    private RedisTemplate redisTemplate;
    private SysConfigService sysConfigService;

    @Autowired
    public ProxyInfoServiceImpl(OkHttpUtil okHttpUtil, RedisTemplate redisTemplate, SysConfigService sysConfigService) {
        this.okHttpUtil = okHttpUtil;
        this.redisTemplate = redisTemplate;
        this.sysConfigService = sysConfigService;
    }


    @Override
    public ProxyInfoDto get() {
        Object value = redisTemplate.opsForValue().get("system:proxy");
        if (value != null) {
            return ((JSONObject) value).toJavaObject(ProxyInfoDto.class);
        }
        synchronized (lock) {
            value = redisTemplate.opsForValue().get("system:proxy");
            if (value != null) {
                return ((JSONObject) value).toJavaObject(ProxyInfoDto.class);
            }
            SysConfig config = sysConfigService.findByBusinessAndCode("proxy", "url");
            String resStr = okHttpUtil.get(config.getContent(), false);
            if (StringUtils.isNotBlank(resStr)) {
                JSONObject resJson = JSON.parseObject(resStr);
                if (resJson.getBoolean("success")) {
                    List<ProxyInfoDto> proxys = resJson.getJSONArray("data").toJavaList(ProxyInfoDto.class);
                    if (proxys != null && !proxys.isEmpty()) {
                        ProxyInfoDto proxy = proxys.get(0);
                        long second = DateUtil.between(DateUtil.date(), proxy.getExpireTime(), DateUnit.SECOND);
                        redisTemplate.opsForValue().set("system:proxy", proxy, second);
                        return proxy;
                    }
                }
                try {
                    Thread.sleep(3000);
                    return get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public ProxyInfoDto refresh() {
        redisTemplate.delete("system:proxy");
        return get();
    }
}
