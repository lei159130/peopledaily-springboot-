package com.lee1314.peopledaily.service;

import com.lee1314.peopledaily.entity.dto.ProxyInfoDto;

/**
 * @author leili
 */
public interface ProxyInfoService {
    ProxyInfoDto get();

    ProxyInfoDto refresh();
}
