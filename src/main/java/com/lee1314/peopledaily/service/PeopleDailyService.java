package com.lee1314.peopledaily.service;

import java.util.List;

import com.lee1314.peopledaily.model.po.PeopleDailyPo;

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

	Integer batchInsert(List<PeopleDailyPo> records);

	List<Integer> findIdsBySeminarId(Integer seminarId);

	Integer insert(PeopleDailyPo record);

	PeopleDailyPo selectByPrimaryKey(Integer id);
}
