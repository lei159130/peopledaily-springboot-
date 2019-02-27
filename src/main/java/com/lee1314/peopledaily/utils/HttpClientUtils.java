package com.lee1314.peopledaily.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * HttpClient连接池工具类
 * 
 * @Title: HttpClientUtils
 * @Description:
 * @author: 雷力
 * @date: 2018年12月27日 下午6:36:16
 *
 */
@Component
public class HttpClientUtils {

	private static Integer maxTotal;

	private static Integer defaultMaxPerRoute;

	private static Integer connectTimeout;

	private static Integer connectionRequestTimeout;

	private static Integer socketTimeout;

	private static Integer validateAfterInactivity;

	/**
	 * 实例化连接池
	 * 
	 * @return
	 */
	public static PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
		// 最大连接数
		httpClientConnectionManager.setMaxTotal(maxTotal);
		// 并发数
		httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		// 多少时间后校验
		httpClientConnectionManager.setValidateAfterInactivity(validateAfterInactivity);
		return httpClientConnectionManager;
	}

	/**
	 * 设置连接池管理器
	 * 
	 * @return
	 */
	public static HttpClientBuilder getHttpClientBuilder() {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.setConnectionManager(getHttpClientConnectionManager());

		return httpClientBuilder;
	}

	/**
	 * 获取httpClient
	 * 
	 * @return
	 */
	public static CloseableHttpClient getCloseableHttpClient() {
		return getHttpClientBuilder().build();
	}

	/**
	 * 配置RequestConfig
	 * 
	 * @return
	 */
	public static RequestConfig.Builder getBuilder() {
		RequestConfig.Builder builder = RequestConfig.custom();
		return builder.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
				.setSocketTimeout(socketTimeout);
	}

	/**
	 * 实例化RequestConfig
	 * 
	 * @return
	 */
	public static RequestConfig getRequestConfig() {
		return getBuilder().build();
	}

	@Value("${http.maxTotal}")
	public void setMaxTotal(Integer maxTotal) {
		HttpClientUtils.maxTotal = maxTotal;
	}

	@Value("${http.defaultMaxPerRoute}")
	public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
		HttpClientUtils.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	@Value("${http.connectTimeout}")
	public void setConnectTimeout(Integer connectTimeout) {
		HttpClientUtils.connectTimeout = connectTimeout;
	}

	@Value("${http.connectionRequestTimeout}")
	public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
		HttpClientUtils.connectionRequestTimeout = connectionRequestTimeout;
	}

	@Value("${http.socketTimeout}")
	public void setSocketTimeout(Integer socketTimeout) {
		HttpClientUtils.socketTimeout = socketTimeout;
	}

	@Value("${http.validateAfterInactivity}")
	public void setValidateAfterInactivity(Integer validateAfterInactivity) {
		HttpClientUtils.validateAfterInactivity = validateAfterInactivity;
	}

}
