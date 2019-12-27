package com.lee1314.peopledaily.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lee1314.peopledaily.dao.mapper.PeopleDailyMapper;
import com.lee1314.peopledaily.model.po.PeopleDailyPo;
import com.lee1314.peopledaily.service.PeopleDailyService;

/**
 * 人民日报ServiceImpl
 * 
 * @Title: PeopleDailyServiceImpl
 * @Description:
 * @author: 雷力
 * @date: 2018年12月29日 下午7:23:13
 *
 */
@Service
public class PeopleDailyServiceImpl implements PeopleDailyService {

	@Autowired
	private PeopleDailyMapper mapper;

	@Override
	public Integer batchInsert(List<PeopleDailyPo> records) {
		return mapper.batchInsert(records);
	}

	@Override
	public List<Integer> findIdsBySeminarId(Integer seminarId) {
		return mapper.selectIdsBySeminarId(seminarId);
	}

	@Override
	public Integer insert(PeopleDailyPo record) {
		return mapper.insert(record);
	}

	@Override
	public PeopleDailyPo selectByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}
}
