package com.lee1314.peopledaily;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.lee1314.peopledaily.commons.thread.SaveThread;
import com.lee1314.peopledaily.service.PeopleDailyService;
import com.lee1314.peopledaily.utils.SpringContext;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.lee1314.peopledaily.dao")
public class PeopledailyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeopledailyApplication.class, args);
		ExecutorService service = Executors.newSingleThreadExecutor();
		SaveThread saveThread = new SaveThread("ExecuteQueyeThread", SpringContext.getBean(PeopleDailyService.class));
		service.execute(saveThread);
		service.shutdown();
	}

}
