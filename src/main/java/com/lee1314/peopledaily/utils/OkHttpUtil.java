package com.lee1314.peopledaily.utils;

import com.lee1314.peopledaily.entity.dto.ProxyInfoDto;
import com.lee1314.peopledaily.service.ProxyInfoService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author leili
 */
@Slf4j
@Component
public class OkHttpUtil {

    public String get(String url, boolean isProxy) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        //设置代理
        if (isProxy) {
            ProxyInfoDto proxy = SpringContext.getBean(ProxyInfoService.class).get();
            if (proxy != null) {
                builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.getIp(), proxy.getPort())));
            }
        }

        //创建请求
        Request request = new Request.Builder().url(url).build();
        log.info("请求{}", url);

        //获取响应
        Call call = builder.build().newCall(request);
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

    public String get(String url) {
        return get(url, false);
    }
}
