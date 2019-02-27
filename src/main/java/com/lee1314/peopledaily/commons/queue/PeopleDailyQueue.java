package com.lee1314.peopledaily.commons.queue;

import java.util.concurrent.LinkedBlockingQueue;

import com.lee1314.peopledaily.commons.cache.PeopleDailyCache;
import com.lee1314.peopledaily.commons.thread.ExecuteQueueThread;
import com.lee1314.peopledaily.service.PeopleDailyService;
import com.lee1314.peopledaily.utils.SpringContextUtil;

/**
 * 队列（单例）
 * 
 * @title PeopleDailyQueue
 * @author 雷力
 * @date 2019年2月26日下午4:39:55
 *
 */
public class PeopleDailyQueue<T> extends LinkedBlockingQueue<T> {

	private static final long serialVersionUID = 1L;

	private static final class singletonHolder {
		private static PeopleDailyQueue<String> INSTANCE = new PeopleDailyQueue<>();
	}

	public static LinkedBlockingQueue<String> getInstance() {
		return singletonHolder.INSTANCE;
	}

	@Override
	public void put(T e) throws InterruptedException {
		super.put(e);
		if (PeopleDailyCache.get("startUp") == null) {
			PeopleDailyCache.set("startUp", true);
		}
		if (Boolean.valueOf(PeopleDailyCache.get("startUp").toString())) {
			new ExecuteQueueThread(SpringContextUtil.getBean(PeopleDailyService.class)).start();
		}
	}

}
