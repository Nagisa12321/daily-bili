package com.jtchen.domain;

import com.alibaba.fastjson.JSONObject;
import com.jtchen.util.Request;
import com.jtchen.util.loadUtil;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/2/4 0:15
 */
public class Video {
    private static final Logger logger = Logger.getLogger(Video.class);
    private static final Properties URLProperties = loadUtil.getURLProperties();
    private final String aid; // av号
    private final String uid; // up主id
    private final String name; // up主名字

    public Video(String aid, String uid, String name) {
        this.aid = aid;
        this.uid = uid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Video{" + "aid=" + aid +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                '}';
    }

    public String getAid() {
        return aid;
    }

    public String getUid() {
        return uid;
    }

    public int getAvCoins() {
        JSONObject j = new JSONObject();
        j.put("aid", aid);
        JSONObject jsonObject = Request.get(URLProperties.getProperty("coin"), j);
        if (jsonObject == null) {
            logger.warn("获取" + aid + "投币信息失败!");
            return 2;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        return data.getInteger("multiply");
    }

    public String getName() {
        return name;
    }
}
