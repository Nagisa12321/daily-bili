package com.jtchen.task;

import com.alibaba.fastjson.JSONObject;
import com.jtchen.domain.User;
import com.jtchen.util.Request;
import com.jtchen.util.loadUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Properties;

import static com.jtchen.util.CheckUtil.checkEnv;
import static org.junit.Assert.*;

public class CoinTaskTest {
    private static final Logger logger = Logger.getLogger(CoinTaskTest.class);

    @Test
    public void testGetCoin1() {
        logger.setLevel(Level.ALL);
        if (checkEnv() == null) {
            logger.error("BILI_JCT, SESSDATA, DedeUserID没有配置");
            return;
        }

        String aid = String.valueOf(500376181);
        Properties urlProperties = loadUtil.getURLProperties();

        JSONObject j = new JSONObject();
        j.put("aid", aid);
        JSONObject object = Request.get(urlProperties.getProperty("coin"), j);
        System.out.println(object);
    }

}