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
import java.net.ProtocolException;
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

    public static JSONObject get(String url) throws ProtocolException {
        return get(url, new JSONObject());
    }

    public static JSONObject get(String url, JSONObject data) throws ProtocolException {
        var getRequest = getBasicConnection(url, HttpGet.METHOD_NAME, data);
        if (getRequest == null) {
            logger.error("getRequest获取错误");
            return null;
        }
        return Client(getRequest);
    }

    public static JSONObject post(String url, JSONObject data) throws ProtocolException {
        var postRequest = getBasicConnection(url, HttpPost.METHOD_NAME, data);
        if (postRequest == null) {
            logger.error("getRequest获取错误");
            return null;
        }
        postRequest.addHeader("accept", connProperties.getProperty("accept"));
        postRequest.addHeader("Content-Type", connProperties.getProperty("Content-Type"));
        postRequest.addHeader("charset", connProperties.getProperty("charset"));

        return Client(postRequest);
    }

    private static HttpUriRequest getBasicConnection(String url, String method, JSONObject data) {
        if (cookie == null) {
            logger.error("Request没有录入相应cookie");
            return null;
        }

        return RequestBuilder.create(method)
                .addHeader("Referer", connProperties.getProperty("referer"))
                .addHeader("Connection", connProperties.getProperty("connection"))
                .addHeader("User-Agent", connProperties.getProperty("User-Agent"))
                .addHeader("Cookie", cookie.toString())
                .setUri(url)
                .addParameters(getPairList(data))
                .build();

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
