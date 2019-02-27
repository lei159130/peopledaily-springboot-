package com.lee1314.peopledaily.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json工具类
 * 
 * @Title: JsonUtils
 * @Description:
 * @author: 雷力
 * @date: 2018年12月29日 下午7:32:23
 *
 */
public class JsonUtils {

	private static GsonBuilder gsonBuilder;

	public synchronized static GsonBuilder getGsonBuilder() {
		if (gsonBuilder == null) {
			synchronized (GsonBuilder.class) {
				if (gsonBuilder == null) {
					gsonBuilder = new GsonBuilder();
				}
			}
		}
		return gsonBuilder;
	}

	public static Gson getGson() {
		return getGsonBuilder().create();
	}

	/**
	 * json字符串转对象
	 * 
	 * @param str
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String str, Class<T> clazz) {
		return getGson().fromJson(str, clazz);
	}

	/**
	 * 对象转json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return getGson().toJson(obj);
	}

}
