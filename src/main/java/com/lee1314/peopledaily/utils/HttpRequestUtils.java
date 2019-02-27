package com.lee1314.peopledaily.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 网络请求工具类
 * 
 * @Title: HttpRequestUtils
 * @Description:
 * @Auther: 雷力
 * @Date: 2018-10-25 17:16:55
 *
 */
public class HttpRequestUtils {

	/**
	 * Get有参,默认编码请求
	 * 
	 * @param url   链接
	 * @param param 参数
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NetworkParameterException
	 */
	public static String doGet(String url, Map<String, String> param) {
		return doGet(url, param, "UTF-8");
	}

	/**
	 * Post有参,默认编码请求
	 * 
	 * @param url         链接
	 * @param param       参数
	 * @param contentType 请求类型
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NetworkParameterException
	 */
	public static String doPost(String url, Map<String, String> param, String contentType) {
		return doPost(url, param, contentType, "UTF-8");
	}

	/**
	 * Get有参请求
	 * 
	 * @param url    链接
	 * @param param  参数
	 * @param encode 编码
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NetworkParameterException
	 */
	public static String doGet(String url, Map<String, String> param, String encode) {

		CloseableHttpClient httpClient = HttpClientUtils.getCloseableHttpClient();
		String result = "";

		try {
			URIBuilder builder = new URIBuilder(url);

			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}

			URI uri = builder.build();

			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse httpResponse = null;

			try {
				httpResponse = httpClient.execute(httpGet);
			} catch (Exception e) {
				e.printStackTrace();
				return doGet(url, param, encode);
			}

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity(), encode);
			} else {
				throw new RuntimeException("StatusCode:" + httpResponse.getStatusLine().getStatusCode() + ";请求错误!");
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Post有参请求
	 * 
	 * @param url         链接
	 * @param param       参数
	 * @param contentType 请求类型
	 * @param encode      编码
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NetworkParameterException
	 */
	public static String doPost(String url, Map<String, String> param, String contentType, String encode) {

		CloseableHttpClient httpClient = HttpClientUtils.getCloseableHttpClient();
		String result = "";

		HttpPost httpPost = new HttpPost(url);
		try {
			if (param != null) {
				if (contentType != null) {
					StringEntity entity = new StringEntity(JsonUtils.toJson(param));
					entity.setContentType(contentType);
					httpPost.setEntity(entity);
				} else {
					List<NameValuePair> values = new ArrayList<>();
					for (String key : param.keySet()) {
						values.add(new BasicNameValuePair(key, param.get(key)));
					}
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, encode);
					httpPost.setEntity(entity);
				}
			}

			CloseableHttpResponse httpResponse = null;
			try {
				httpResponse = httpClient.execute(httpPost);
			} catch (Exception e) {
				e.printStackTrace();
				return doGet(url, param, encode);
			}

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity(), encode);
			} else {
				throw new RuntimeException("StatusCode:" + httpResponse.getStatusLine().getStatusCode() + ";请求错误!");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
