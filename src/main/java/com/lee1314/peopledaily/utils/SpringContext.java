package com.lee1314.peopledaily.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {
	private static final class singleton {
		private static ApplicationContext context;
	}

	public static ApplicationContext getInstance() {
		return singleton.context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		singleton.context = applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		return singleton.context.getBean(clazz);
	}

	public static <T> T getBean(Class<T> clazz, Object... args) {
		return singleton.context.getBean(clazz, args);
	}
}
