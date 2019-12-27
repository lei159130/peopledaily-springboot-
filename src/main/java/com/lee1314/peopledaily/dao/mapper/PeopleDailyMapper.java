package com.lee1314.peopledaily.dao.mapper;

import java.util.List;

import com.lee1314.peopledaily.model.po.PeopleDailyPo;

public interface PeopleDailyMapper {
	Integer deleteByPrimaryKey(Integer id);

	Integer insert(PeopleDailyPo record);

	PeopleDailyPo selectByPrimaryKey(Integer id);

	List<PeopleDailyPo> selectAll();

	Integer updateByPrimaryKey(PeopleDailyPo record);

	Integer batchInsert(List<PeopleDailyPo> records);

	List<Integer> selectIdsBySeminarId(Integer seminarId);
}