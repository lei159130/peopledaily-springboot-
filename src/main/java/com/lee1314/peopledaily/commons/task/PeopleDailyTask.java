package com.lee1314.peopledaily.commons.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
		JSONArray arrays = JSON.parseArray(ConfigCache.get("peopledaily_paramvalue"));
		List<String> seminarIds = arrays.stream().map(obj -> ((JSONObject) obj).getString("id"))
				.collect(Collectors.toList());
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
