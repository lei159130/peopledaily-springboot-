package com.lee1314.peopledaily.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lee1314.peopledaily.dao.PeopleDailyMapper;
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
	private PeopleDailyMapper Mapper;

	@Override
	public Integer batchInsert(List<Object> maps) {
		return Mapper.batchInsert(maps);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer insertByMap(Map map) {
		return Mapper.insertByMap(map);
	}

	@Override
	public List<Integer> findAllIds(Integer code) {
		return Mapper.selectAllIds(code);
	}

}
