package com.lee1314.peopledaily.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	private final static String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	private static ThreadLocal<Calendar> localCalendar = new ThreadLocal<Calendar>();
	@SuppressWarnings("static-access")
	private static Calendar calendar = localCalendar.get().getInstance();

	/**
	 * 时间戳转日期
	 * 
	 * @param millis 时间戳
	 * @return
	 */
	public static String getTime(Long millis) {
		return getTime(millis, defaultDateFormat);
	}

	/**
	 * 时间戳转日期
	 * 
	 * @param millis  时间戳
	 * @param pattern 日期格式
	 * @return
	 */
	public static String getTime(Long millis, String pattern) {
		calendar.setTimeInMillis(millis);
		sdf.applyPattern(defaultDateFormat);
		return sdf.format(calendar.getTime());
	}
}
