package com.lee1314.peopledaily.commons.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lee1314.peopledaily.commons.cache.PeopleDailyCache;
import com.lee1314.peopledaily.commons.thread.PeopleDailyThread;
import com.lee1314.peopledaily.reptile.PeopleDailyReptile;
import com.lee1314.peopledaily.service.PeopleDailyService;

/**
 * 爬虫定时器
 * 
 * @Title: PeopleDailyTask
 * @Description:
 * @author: 雷力
 * @date: 2019年1月22日 下午12:28:53
 *
 */
@Component
public class PeopleDailyTask {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${peopledaily.paramvalue}")
	private String paramvalue;

	@Autowired
	private PeopleDailyReptile reptile;
	@Autowired
	private PeopleDailyService peopleDailyService;

	@Scheduled(cron = "00 42 18 * * ?")
	public void excute() {
		String[] paramValues = paramvalue.split(",");
		for (String paramValue : paramValues) {
			int code = Integer.valueOf(paramValue);
			PeopleDailyCache.set("haveMore" + code, true);
			PeopleDailyCache.set("execute" + code, true);
			PeopleDailyCache.set("ids" + code, peopleDailyService.findAllIds(code));
			logger.info("开辟PeopleDailyThread{}", code);
			new PeopleDailyThread(reptile, code).start();
		}
	}
}
