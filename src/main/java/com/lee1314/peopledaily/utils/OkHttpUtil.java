package com.lee1314.peopledaily.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class OkHttpUtil {
	private static OkHttpClient client = new OkHttpClient();

	public static String get(String url, Map<String, String> param) {
		if (param != null && !param.isEmpty()) {
			Iterator<Entry<String, String>> entrys = param.entrySet().iterator();
			StringBuffer sb = new StringBuffer();
			sb.append(url);
			sb.append("?");
			while (entrys.hasNext()) {
				Entry<String, String> entry = entrys.next();
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(entry.getValue());
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
			url = sb.toString();
		}

		Request request = new Request.Builder().url(url).build();
		log.info("请求{}", url);
		
		Call call = client.newCall(request);
		Response response = null;
		try {
			response = call.execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (IOException e) {
			log.error("请求失败，request:{}", url);
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
}
