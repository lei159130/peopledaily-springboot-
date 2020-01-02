package com.lee1314.peopledaily.commons.thread;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.lee1314.peopledaily.commons.cache.IdsCache;
import com.lee1314.peopledaily.commons.queue.PeopleDailyQueue;
import com.lee1314.peopledaily.model.dto.PeopleDailyDto;
import com.lee1314.peopledaily.model.po.PeopleDailyPo;
import com.lee1314.peopledaily.service.PeopleDailyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveThread extends Thread {
	private PeopleDailyService peopleDailyService;

	public SaveThread(String name, PeopleDailyService peopleDailyService) {
		super(name);
		this.peopleDailyService = peopleDailyService;
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			String json;
			try {
				json = PeopleDailyQueue.getInstance().take();
				log.info("take queue:{}", json);
			} catch (InterruptedException e) {
				log.error("take queue error");
				json = null;
				e.printStackTrace();
				break;
			}
			if (StringUtils.isEmpty(json)) {
				continue;
			}

			PeopleDailyDto dto = JSONObject.parseObject(json, PeopleDailyDto.class);

			String audioUrl = dto.getAudio_url();
			if (StringUtils.isEmpty(audioUrl)) {
				continue;
			}
			if (audioUrl.indexOf("http") == -1 || audioUrl.indexOf("https") == -1) {
				dto.setAudio_url("https://rmrbcmsonline.peopleapp.com" + audioUrl);
			}

			Document doc = null;
			int count = 0;
			while (count < 5) {
				try {
					doc = Jsoup.parse(new URL(dto.getShare_url()), 10000);
					break;
				} catch (IOException e) {
					if (count == 0) {
						log.error("url:{}页面解析失败...，重试解析...", dto.getShare_url());
					} else if (count == 5) {
						log.error("url:{}页面解析失败...，请检查网络连接", dto.getShare_url());
					} else {
						log.error("第{}次重试中...", ++count);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					continue;
				}
			}
			Element e = doc.getElementsByClass("article").first();
			String content = e.html();
			content = content.replace("&lt;", "<");
			content = content.replace("&gt;", ">");
			dto.setContent(content);

			log.info("dto:{}", dto);

			PeopleDailyPo po = new PeopleDailyPo();// TODO:待优化，属性复制
			po.setAudioPlayTime(dto.getAudio_play_time());
			po.setAudioUrl(dto.getAudio_url());
			po.setCommentCount(Integer.parseInt(dto.getComment_count()));
			po.setContent(dto.getContent());
			po.setId(Integer.parseInt(dto.getId()));
			po.setSeminarId(Integer.parseInt(dto.getSeminar_id()));
			po.setShareUrl(dto.getShare_url());
			po.setTitle(dto.getTitle());

			if (peopleDailyService.selectByPrimaryKey(po.getId()) == null) {
				Integer num = peopleDailyService.insert(po);
				if (num > 0) {
					IdsCache.get("ids" + po.getSeminarId()).add(po.getId());
				} else {
					log.error("insert into error,{}", po);
				}
			}
		}
	}
}
