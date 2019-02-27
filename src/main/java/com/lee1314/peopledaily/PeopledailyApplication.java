package com.lee1314.peopledaily;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.lee1314.peopledaily.utils.SpringContextUtil;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.lee1314.peopledaily.dao")
public class PeopledailyApplication {

	public static void main(String[] args) {
		SpringContextUtil.setApplicationContext(SpringApplication.run(PeopledailyApplication.class, args));
	}

}
