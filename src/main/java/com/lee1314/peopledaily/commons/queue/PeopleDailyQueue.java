package com.lee1314.peopledaily.commons.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class PeopleDailyQueue extends LinkedBlockingQueue<String> {

	private static final long serialVersionUID = 1L;
	private static int TOTAL = 1000;

	public PeopleDailyQueue(int capacity) {
		super(capacity);
	}

	private static final class singletonHolder {
		private static PeopleDailyQueue INSTANCE = new PeopleDailyQueue(TOTAL);
	}

	public static LinkedBlockingQueue<String> getInstance() {
		return singletonHolder.INSTANCE;
	}
}
