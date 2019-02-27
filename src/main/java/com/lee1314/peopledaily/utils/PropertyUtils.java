package com.lee1314.peopledaily.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Property工具类
 * 
 * @Title: PropertyUtils
 * @Description:
 * @author: 雷力
 * @date: 2018年12月29日 上午10:26:58
 *
 */
public class PropertyUtils extends PropertyPlaceholderConfigurer {

	private static Map<String, String> propertyMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		propertyMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			propertyMap.put(keyStr, value);
		}
	}

	public static String getStringProperty(String name) {
		return propertyMap.get(name);
	}

	public static Integer getIntegerProperty(String key) {
		return Integer.valueOf(propertyMap.get(key));
	}
}
