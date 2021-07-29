package com.lee1314.peopledaily.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author leili
 */
@Data
public class ProxyInfoDto {

    private String ip;

    private Integer port;

    @JSONField(name = "expire_time", format = "yyyy-MM-dd HH-mm-ss")
    private Date expireTime;
}
