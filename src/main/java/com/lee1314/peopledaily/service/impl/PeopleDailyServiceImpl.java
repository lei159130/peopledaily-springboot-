package com.lee1314.peopledaily.service.impl;

import com.lee1314.peopledaily.dao.mapper.PeopleDailyMapper;
import com.lee1314.peopledaily.entity.PeopleDaily;
import com.lee1314.peopledaily.service.PeopleDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人民日报ServiceImpl
 *
 * @Title: PeopleDailyServiceImpl
 * @Description:
 * @author: 雷力
 * @date: 2018年12月29日 下午7:23:13
 */
@Service
public class PeopleDailyServiceImpl implements PeopleDailyService {

    private PeopleDailyMapper mapper;

    @Autowired
    public PeopleDailyServiceImpl(PeopleDailyMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Integer> findIdsBySeminarId(Integer seminarId) {
        return mapper.selectIdsBySeminarId(seminarId);
    }

    @Override
    public Integer insert(PeopleDaily entity) {
        return mapper.insert(entity);
    }
}
