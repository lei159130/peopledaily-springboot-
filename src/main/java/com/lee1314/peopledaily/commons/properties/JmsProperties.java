package com.lee1314.peopledaily.commons.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author leili
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.activemq")
public class JmsProperties {
    private String userName;
    private String password;
    private String brokerUrl;
    private String reptileQueue;
    private String saveQueue;
}
