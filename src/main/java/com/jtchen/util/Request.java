package com.jtchen.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/28 23:35
 */
public class Request {
    public static Logger logger = Logger.getLogger(Request.class);
    public static Properties properties;
    public static Cookie cookie;

    static {
        try {
            var inputStream = Request.class.getClassLoader().getResourceAsStream("connect.properties");
            properties = new Properties();
            properties.load(inputStream);

        } catch (IOException e) {
            logger.error("inputStream流获取错误" + e);
        }
    }

    public static void setCookie(Cookie cookie) {
        Request.cookie = cookie;
    }

    public static JSONObject get(String url) throws ProtocolException {
        HttpURLConnection huc = getBasicConnection(url);
        if (huc == null) {
            logger.error("basicConnection获取错误");
            return null;
        }
        huc.setRequestMethod("GET");
        return Client(huc);
    }

    private static HttpURLConnection getBasicConnection(String url) throws ProtocolException {
        try {
            if (cookie == null) {
                logger.error("Request没有录入相应cookie");
                return null;
            }

            URL u = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            // 请求头
            huc.setRequestProperty("Referer", properties.getProperty("referer"));
            huc.setRequestProperty("Connection", properties.getProperty("connection"));
            huc.setRequestProperty("User-Agent", properties.getProperty("User-Agent"));
            huc.setRequestProperty("Cookie", cookie.toString());

            return huc;
        } catch (MalformedURLException e) {
            logger.error("url无法识别", e);
        } catch (IOException e) {
            logger.error("无法打开Connection", e);
        }
        return null;
    }

    private static JSONObject Client(HttpURLConnection huc) {
        try {
            int responseCode = huc.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                logger.error("响应码为" + responseCode);
                return null;
            }
            //得到响应流
            InputStream inputStream = huc.getInputStream();
            //将响应流转换成字符串
            String result = new Scanner(inputStream).useDelimiter("\\Z").next();//将流转换为字符串。
            return JSONObject.parseObject(result);
        } catch (IOException e) {
            logger.error(e);
            return null;
        }
    }
}
