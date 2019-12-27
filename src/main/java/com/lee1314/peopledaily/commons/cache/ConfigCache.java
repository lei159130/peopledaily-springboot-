package com.lee1314.peopledaily.commons.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigCache {
	private final static Map<String, String> cacheMap = new ConcurrentHashMap<>();

	public static void set(String key, String value) {
		cacheMap.put(key, value);
	}

	public static void set(Map<String, String> configs) {
		Iterator<Entry<String, String>> iterators = configs.entrySet().iterator();
		while (iterators.hasNext()) {
			Entry<String, String> entry = iterators.next();
			cacheMap.put(entry.getKey(), entry.getValue());
		}
	}

	public static String get(String key) {
		return cacheMap.get(key);
	}

	public static void remove(String key) {
		cacheMap.remove(key);
	}

	public static void clear() {
		cacheMap.clear();
	}
}
