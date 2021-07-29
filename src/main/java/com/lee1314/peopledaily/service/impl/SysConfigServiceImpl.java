package com.lee1314.peopledaily.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee1314.peopledaily.dao.mapper.SysConfigMapper;
import com.lee1314.peopledaily.entity.SysConfig;
import com.lee1314.peopledaily.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author leili
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    private SysConfigMapper sysConfigMapper;
    private RedisTemplate redisTemplate;

    @Autowired
    public SysConfigServiceImpl(SysConfigMapper sysConfigMapper, RedisTemplate redisTemplate) {
        this.sysConfigMapper = sysConfigMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public SysConfig findByBusinessAndCode(String business, String code) {
        String k = "system:config", hk = business + "_" + code;
        Object value = redisTemplate.opsForHash().get(k, hk);
        if (value != null) {
            return ((JSONObject) value).toJavaObject(SysConfig.class);
        }
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getBusiness, business).eq(SysConfig::getCode, code);
        SysConfig sysConfig = sysConfigMapper.selectOne(wrapper);
        if (sysConfig != null) {
            redisTemplate.opsForHash().put(k, hk, sysConfig);
            return sysConfig;
        }
        return null;
    }
}
