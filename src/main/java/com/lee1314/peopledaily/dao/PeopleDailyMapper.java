package com.lee1314.peopledaily.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lee1314.peopledaily.model.PeopleDaily;

/**
 * 人民日报Mapper
 * 
 * @Title: PeopleDailyMapper
 * @Description:
 * @author: 雷力
 * @date: 2018年12月29日 下午7:22:51
 *
 */
public interface PeopleDailyMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(PeopleDaily record);

	int insertSelective(PeopleDaily record);

	PeopleDaily selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(PeopleDaily record);

	int updateByPrimaryKeyWithBLOBs(PeopleDaily record);

	int updateByPrimaryKey(PeopleDaily record);

	/**
	 * 批量添加
	 * 
	 * @param peopleDailys
	 * @return
	 */
	Integer batchInsert(@Param("maps") List<Object> maps);

	/**
	 * 添加非空字段
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Integer insertByMap(Map map);

	/**
	 * 获取所有Id
	 * 
	 * @return
	 */
	List<Integer> selectAllIds(@Param("code") Integer code);
}