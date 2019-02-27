package com.lee1314.peopledaily.commons.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存类
 * 
 * @Title: PeopleDailyCache
 * @Description:
 * @author: 雷力
 * @date: 2019年1月24日 上午11:31:55
 *
 */
public class PeopleDailyCache {
	private static Map<String, Object> cacheMap = new ConcurrentHashMap<>();

	public static void destoryCacheMap() {
		cacheMap = null;
	}

	public static Map<String, Object> getCacheMap() {
		return cacheMap;
	}

	public static void set(String key, Object values) {
		cacheMap.put(key, values);
	}

	public static Object get(String key) {
		return cacheMap.get(key);
	}

	public static String getString(String key) {
		return (String) cacheMap.get(key);
	}

	public static Object getToEmpty(String key) {
		Object o = cacheMap.get(key);
		if (o == null)
			return "";
		else
			return o;
	}

	public static void remove(String key) {
		cacheMap.remove(key);
	}

	public static void clear() {
		cacheMap.clear();
	}
}
