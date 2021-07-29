package com.lee1314.peopledaily.service;


import com.lee1314.peopledaily.entity.SysConfig;

/**
 * @author leili
 */
public interface SysConfigService {

    SysConfig findByBusinessAndCode(String business, String code);

}
