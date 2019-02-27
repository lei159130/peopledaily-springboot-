package com.lee1314.peopledaily.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * ApplicationContext工具类
 * 
 * @Title: ApplicationContextUtils
 * @Description:
 * @author: 雷力
 * @date: 2018年12月29日 上午10:26:28
 *
 */
@Component
public class SpringContextUtil {
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String className) {
		return applicationContext.getBean(className);
	}

	public static Object getBean(String className, Object... objs) {
		return applicationContext.getBean(className, objs);
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public static <T> T getBean(Class<T> clazz, Object... objs) {
		return applicationContext.getBean(clazz, objs);
	}
}
