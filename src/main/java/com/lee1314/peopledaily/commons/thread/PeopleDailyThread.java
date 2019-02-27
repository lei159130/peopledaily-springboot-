package com.lee1314.peopledaily.commons.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee1314.peopledaily.commons.cache.PeopleDailyCache;
import com.lee1314.peopledaily.reptile.PeopleDailyReptile;

/**
 * 爬取数据线程
 * 
 * @title PeopleDailyThread
 * @author 雷力
 * @date 2019年2月26日上午11:21:03
 *
 */
public class PeopleDailyThread extends Thread {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private PeopleDailyReptile reptile;

	private Integer code;

	public PeopleDailyThread(PeopleDailyReptile reptile, Integer code) {
		super();
		this.reptile = reptile;
		this.code = code;
		this.setName("PeopleDailyThread-" + code);
	}

	@Override
	public void run() {
		Integer page = 1;
		boolean exit = true;
		while (exit) {
			boolean execute = (boolean) PeopleDailyCache.get("execute" + code);
			boolean haveMore = (boolean) PeopleDailyCache.get("haveMore" + code);
			logger.info("PeopleDailyThread:{}，execute:{},haveMore:{}", code, execute, haveMore);
			if (!execute) {
				exit = false;
				logger.info("PeopleDailyThread{}退出", code);
				break;
			}
			if (!haveMore) {
				exit = false;
				logger.info("PeopleDailyThread{}退出", code);
				break;
			}
			logger.info("获取{}第{}页数据", code, page);
			reptile.QueryLists(code, page++);
		}
	}

}
