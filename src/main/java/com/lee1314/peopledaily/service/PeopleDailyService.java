package com.lee1314.peopledaily.service;

import com.lee1314.peopledaily.entity.PeopleDaily;

import java.util.List;


/**
 * @author leili
 */
public interface PeopleDailyService {

    /**
     * 根据SeminarId查询所有id
     *
     * @param seminarId
     * @return
     */
    List<Integer> findIdsBySeminarId(Integer seminarId);

    /**
     * 插入数据
     *
     * @param record
     * @return
     */
    Integer insert(PeopleDaily record);
}
