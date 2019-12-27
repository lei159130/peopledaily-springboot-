package com.lee1314.peopledaily.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lee1314.peopledaily.dao.mapper.SysConfigMapper;
import com.lee1314.peopledaily.model.po.SysConfigPo;
import com.lee1314.peopledaily.service.SysConfigService;

@Service
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Override
	public List<SysConfigPo> selectByBusiness(String business) {
		return sysConfigMapper.selectByBusiness(business);
	}

}
