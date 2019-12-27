package com.lee1314.peopledaily.commons.config;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.lee1314.peopledaily.commons.cache.ConfigCache;
import com.lee1314.peopledaily.commons.cache.IdsCache;
import com.lee1314.peopledaily.service.PeopleDailyService;
import com.lee1314.peopledaily.service.SysConfigService;

@Configuration
public class SysConfigLoading implements CommandLineRunner {

	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private PeopleDailyService peopleDailyService;

	@Override
	public void run(String... args) throws Exception {
		Map<String, String> configs = sysConfigService.selectByBusiness("peopledaily").stream()
				.collect(Collectors.toMap(k -> k.getBusiness() + "_" + k.getCode(), v -> v.getContent()));
		ConfigCache.set(configs);
		for (String seminarId : configs.get("peopledaily_paramvalue").split(",")) {
			IdsCache.set("ids" + seminarId, peopleDailyService.findIdsBySeminarId(Integer.parseInt(seminarId)));
		}
	}
}
