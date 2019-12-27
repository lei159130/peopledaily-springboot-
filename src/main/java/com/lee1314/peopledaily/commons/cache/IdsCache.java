package com.lee1314.peopledaily.commons.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IdsCache {
	private final static Map<String, List<Integer>> cacheMap = new ConcurrentHashMap<>();

	public static void set(String key, List<Integer> value) {
		cacheMap.put(key, value);
	}

	public static List<Integer> get(String key) {
		return cacheMap.get(key);
	}

	public static void remove(String key) {
		cacheMap.remove(key);
	}

	public static void clear() {
		cacheMap.clear();
	}
}
