package com.lee1314.peopledaily.commons.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lee1314.peopledaily.commons.cache.ConfigCache;
import com.lee1314.peopledaily.reptile.PeopleDailyReptile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PeopleDailyTask {
	@Autowired
	private PeopleDailyReptile reptile;

	@Scheduled(cron = "00 00 00 * * ?")
	public void excute() {
		String[] seminarIds = ConfigCache.get("peopledaily_paramvalue").split(",");
		ExecutorService service = Executors.newCachedThreadPool();
		for (String seminarId : seminarIds) {
			log.info("开辟PeopleDailyThread{}", seminarId);
			service.execute(new Thread("PeopleDailyThread" + seminarId) {
				private ThreadLocal<Integer> page = new ThreadLocal<>();

				@Override
				public void run() {
					while (!isInterrupted()) {
						Integer page;
						if (this.page.get() == null) {
							page = 0;
						} else {
							page = this.page.get();
						}

						log.info("获取{}第{}页数据", seminarId, page);
						boolean bool = reptile.QueryLists(seminarId, page++);
						this.page.set(page);

						if (!bool) {
							break;
						}
					}
				}
			});
		}
		service.shutdown();
	}
}
