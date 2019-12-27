package com.lee1314.peopledaily.service;

import java.util.List;

import com.lee1314.peopledaily.model.po.SysConfigPo;

public interface SysConfigService {

	List<SysConfigPo> selectByBusiness(String business);

}
