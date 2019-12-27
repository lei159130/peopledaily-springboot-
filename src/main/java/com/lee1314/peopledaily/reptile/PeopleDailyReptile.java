package com.lee1314.peopledaily.reptile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lee1314.peopledaily.commons.cache.ConfigCache;
import com.lee1314.peopledaily.commons.cache.IdsCache;
import com.lee1314.peopledaily.commons.queue.PeopleDailyQueue;
import com.lee1314.peopledaily.model.dto.PeopleDailyDto;
import com.lee1314.peopledaily.utils.OkHttpUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 人民日报爬虫
 * 
 * @Title: PeopleDailyReptile
 * @Description:
 * @author: 雷力
 * @date: 2018年12月29日 下午7:29:49
 *
 */
@Slf4j
@Component
public class PeopleDailyReptile {
	/**
	 * 查询多条数据
	 * 
	 * @param value
	 * @param page
	 * @return
	 */
	public boolean QueryLists(String seminarId, Integer page) {
		String url = ConfigCache.get("peopledaily_listenListAPI");
		Map<String, String> param = new HashMap<String, String>();
		param.put("page", page.toString());
		param.put("refresh_time", "0");
		param.put(ConfigCache.get("peopledaily_paramname"), String.valueOf(seminarId));

		String result = OkHttpUtil.get(url, param);
		if (StringUtils.isEmpty(result)) {
			return false;
		}

		JSONObject json = JSON.parseObject(result);

		JSONObject res = json.getJSONObject("result");
		List<PeopleDailyDto> data = json.getJSONArray("data").toJavaList(PeopleDailyDto.class);

		if (!"0".equals(res.getString("errorCode"))) {
			log.error("==== {}?{}====>服务器返回错误!", url, param);
			return false;
		}

		// 推送队列
		for (PeopleDailyDto dto : data) {
			if (IdsCache.get("ids" + seminarId).contains(Integer.parseInt(dto.getId()))) {
				return false;
			}

			dto.setSeminar_id(seminarId);
			String jsonDto = JSON.toJSONString(dto);
			try {
				PeopleDailyQueue.getInstance().put(jsonDto);
				log.info("put queue:{}", jsonDto);
			} catch (InterruptedException e) {
				log.error("put queue error:{}", jsonDto);
				e.printStackTrace();
				return false;
			}
		}
		return res.getBoolean("have_more");
	}

}
