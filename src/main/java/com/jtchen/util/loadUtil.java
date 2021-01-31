package com.jtchen.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/30 23:16
 */
public class loadUtil {
    private static final Logger logger = Logger.getLogger(loadUtil.class);
    private static Properties URLProperties = null;
    private static Properties ConnectProperties = null;
    private static Properties TaskProperties = null;

    public static Properties getTaskProperties() {
        if (TaskProperties == null) {
            TaskProperties = getProperties("task");
        }
        return TaskProperties;
    }

    public static Properties getURLProperties() {
        if (URLProperties == null) {
            URLProperties = getProperties("url");
        }
        return URLProperties;
    }

    public static Properties getConnectProperties() {
        if (ConnectProperties == null) {
            ConnectProperties = getProperties("connect");
        }
        return ConnectProperties;
    }

    private static Properties getProperties(String type) {
        Properties properties = null;
        try {
            var inputStream = Request.class.getClassLoader()
                    .getResourceAsStream(type + ".properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("url.Properties加载错误" + e);
        }
        return properties;
    }
}
