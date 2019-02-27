package com.lee1314.peopledaily.commons.thread;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee1314.peopledaily.commons.cache.PeopleDailyCache;
import com.lee1314.peopledaily.commons.queue.PeopleDailyQueue;
import com.lee1314.peopledaily.service.PeopleDailyService;
import com.lee1314.peopledaily.utils.HttpRequestUtils;
import com.lee1314.peopledaily.utils.JsonUtils;

/**
 * 提取队列数据线程
 * 
 * @title ExecuteQueyeThread
 * @author 雷力
 * @date 2019年2月26日上午11:20:23
 *
 */
public class ExecuteQueueThread extends Thread {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private PeopleDailyService peopleDailyService;

	public ExecuteQueueThread(PeopleDailyService peopleDailyService) {
		super();
		this.setName("ExecuteQueyeThread");
		this.peopleDailyService = peopleDailyService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void run() {
		boolean exit = true;
		while (exit) {
			String jsonStr = PeopleDailyQueue.getInstance().poll();
			if (StringUtils.isBlank(jsonStr)) {
				exit = false;
				PeopleDailyCache.set("startUp", false);
				continue;
			}
			Map map = JsonUtils.fromJson(jsonStr, Map.class);

			if (map.containsKey("audio_url")) {
				String audioUrl = map.get("audio_url").toString();
				if (map.get("audio_url").toString().indexOf("http") < 0)
					map.put("audio_url", "https://rmrbcmsonline.peopleapp.com" + audioUrl);
			}

			String share_html = HttpRequestUtils.doGet(map.get("share_url").toString(), null);
			Document doc = Jsoup.parse(share_html);
			Element e = doc.getElementsByClass("article").first();
			String content = e.html();
			content = content.replace("&lt;", "<");
			content = content.replace("&gt;", ">");
			map.put("content", content);

			try {
				Integer num = peopleDailyService.insertByMap(map);
				logger.info("insert into:{}", map.toString());
				if (num > 0) {
					Integer code = Double.valueOf(map.get("seminarId").toString()).intValue();
					List<Integer> ids = (List) PeopleDailyCache.get("ids" + code);
					Integer id = Double.valueOf(map.get("id").toString()).intValue();
					ids.add(id);
					PeopleDailyCache.set("ids" + code, ids);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

		}
	}
}
