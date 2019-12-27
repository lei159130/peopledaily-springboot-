package com.lee1314.peopledaily.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lee1314.peopledaily.model.po.SysConfigPo;

public interface SysConfigMapper {
	int deleteByPrimaryKey(@Param("business") String business, @Param("code") String code);

	int insert(SysConfigPo record);

	SysConfigPo selectByPrimaryKey(@Param("business") String business, @Param("code") String code);

	List<SysConfigPo> selectAll();

	int updateByPrimaryKey(SysConfigPo record);

	List<SysConfigPo> selectByBusiness(@Param("business") String business);
}