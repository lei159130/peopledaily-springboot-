package com.lee1314.peopledaily;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author leili
 */
@EnableJms
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("com.lee1314.peopledaily.dao.mapper")
public class PeopledailyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeopledailyApplication.class, args);
    }

}
