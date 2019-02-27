package com.lee1314.peopledaily.reptile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lee1314.peopledaily.commons.cache.PeopleDailyCache;
import com.lee1314.peopledaily.commons.queue.PeopleDailyQueue;
import com.lee1314.peopledaily.utils.HttpRequestUtils;
import com.lee1314.peopledaily.utils.JsonUtils;

/**
 * 人民日报爬虫
 * 
 * @Title: PeopleDailyReptile
 * @Description:
 * @author: 雷力
 * @date: 2018年12月29日 下午7:29:49
 *
 */
@Component
public class PeopleDailyReptile {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${peopledaily.listenListAPI}")
	private String url;
	@Value("${peopledaily.paramname}")
	private String paramname;

	/**
	 * 查询多条数据
	 * 
	 * @param value
	 * @param page
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void QueryLists(Integer code, Integer page) {
		// 封装参数
		Map<String, String> param = new HashMap<String, String>();
		param.put("page", page.toString());
		param.put("refresh_time", "0");
		param.put(paramname, String.valueOf(code));
		String result = HttpRequestUtils.doPost(url, param, null);

		logger.info("===={}?{}====>{}", url, param.toString(), result);

		// 解析Result
		Map<String, Object> map = JsonUtils.fromJson(result, Map.class);
		Map<String, Object> res = (Map<String, Object>) map.get("result");
		List<Map> data = (List<Map>) map.get("data");

		// 错误处理
		if (!"0".equals(res.get("errorCode"))) {
			logger.error("==== {}?{}====>服务器返回错误!", url, param.toString());
			return;
		}

		// 检测是否还有更多数据
		PeopleDailyCache.set("havaMore" + code, res.get("have_more"));

		List<Integer> ids = (List) PeopleDailyCache.get("ids" + code);

		// 推送队列
		for (Map obj : data) {
			Integer id = Double.valueOf(obj.get("id").toString()).intValue();
			if (ids.contains(id)) {
				PeopleDailyCache.set("execute" + code, false);
				continue;
			}
			obj.put("seminarId", code);
			try {
				String jsonStr = JsonUtils.toJson(obj);
				PeopleDailyQueue.getInstance().put(jsonStr);
				logger.info("put queue:{}", jsonStr);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
