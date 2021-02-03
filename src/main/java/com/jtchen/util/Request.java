package com.jtchen.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.jtchen.util.ParsingUtil.getPairList;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/28 23:35
 */
public class Request {
    private static final Logger logger = Logger.getLogger(Request.class);
    private static final Properties connProperties = loadUtil.getConnectProperties();
    private static Cookie cookie;

    public static void setCookie(Cookie cookie) {
        Request.cookie = cookie;
    }

    public static JSONObject get(String url) {
        return get(url, new JSONObject());
    }

    public static JSONObject get(String url, JSONObject data) {
        var requestBuilder = getBasicConnection(url, HttpGet.METHOD_NAME);
        if (requestBuilder == null) {
            logger.error("requestBuilder获取错误");
            return null;
        }
        requestBuilder.addParameters(getPairList(data));
        return Client(requestBuilder.build());
    }

    public static JSONObject post(String url, JSONObject data) {
        var requestBuilder = getBasicConnection(url, HttpPost.METHOD_NAME);
        if (requestBuilder == null) {
            logger.error("getRequest获取错误");
            return null;
        }
        requestBuilder.addHeader("accept", connProperties.getProperty("accept"))
                .addHeader("Content-Type", connProperties.getProperty("Content-Type"))
                .addHeader("charset", connProperties.getProperty("charset"))
                .addParameters(getPairList(data));

        return Client(requestBuilder.build());
    }

    private static RequestBuilder getBasicConnection(String url, String method) {
        if (cookie == null) {
            logger.error("Request没有录入相应cookie");
            return null;
        }

        return RequestBuilder.create(method)
                .addHeader("Referer", connProperties.getProperty("referer"))
                .addHeader("Connection", connProperties.getProperty("connection"))
                .addHeader("User-Agent", connProperties.getProperty("User-Agent"))
                .addHeader("Cookie", cookie.toString())
                .setUri(url);

    }

    private static JSONObject Client(HttpUriRequest request) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            var response = client.execute(request);
            HttpEntity entity = response.getEntity();
            return JSON.parseObject(EntityUtils.toString(entity, StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error("创建client出现问题");
            return null;
        }
    }
}
