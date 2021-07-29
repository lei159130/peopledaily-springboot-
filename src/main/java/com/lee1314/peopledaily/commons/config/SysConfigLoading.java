package com.lee1314.peopledaily.commons.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lee1314.peopledaily.entity.SysConfig;
import com.lee1314.peopledaily.service.PeopleDailyService;
import com.lee1314.peopledaily.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author leili
 */
@Configuration
public class SysConfigLoading implements CommandLineRunner {

    private final RedisTemplate redisTemplate;
    private final SysConfigService sysConfigService;
    private final PeopleDailyService peopleDailyService;

    @Autowired
    public SysConfigLoading(RedisTemplate redisTemplate, SysConfigService sysConfigService, PeopleDailyService peopleDailyService) {
        this.redisTemplate = redisTemplate;
        this.sysConfigService = sysConfigService;
        this.peopleDailyService = peopleDailyService;
    }

    @Override
    public void run(String... args) {
        SysConfig config = sysConfigService.findByBusinessAndCode("peopledaily", "paramvalue");
        JSONArray arrays = JSON.parseArray(config.getContent());
        for (int i = 0; i < arrays.size(); i++) {
            JSONObject obj = arrays.getJSONObject(i);
            Integer seminarId = obj.getInteger("id");
            String k = "collection:ids:" + seminarId;
            redisTemplate.opsForSet().add(k, peopleDailyService.findIdsBySeminarId(seminarId).toArray(new Integer[]{}));
        }
    }
}
