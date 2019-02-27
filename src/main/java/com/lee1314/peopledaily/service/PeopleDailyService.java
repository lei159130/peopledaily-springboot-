package com.lee1314.peopledaily.service;

import java.util.List;
import java.util.Map;

/**
 * 人民日报Service
 * 
 * @Title: PeopleDailyService
 * @Description:
 * @author: 雷力
 * @date: 2018年12月29日 下午7:30:14
 *
 */
public interface PeopleDailyService {

	/**
	 * 批量添加
	 * 
	 * @param data
	 * @return
	 */
	Integer batchInsert(List<Object> data);

	/**
	 * 单挑添加
	 * 
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	Integer insertByMap(Map map);

	/**
	 * 获取所有ID
	 * 
	 * @return
	 */
	List<Integer> findAllIds(Integer code);

}
