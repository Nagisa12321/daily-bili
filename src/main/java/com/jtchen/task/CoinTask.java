package com.jtchen.task;

import com.alibaba.fastjson.JSONObject;
import com.jtchen.domain.Config;
import com.jtchen.domain.User;
import com.jtchen.task.impl.Task;
import com.jtchen.util.Request;
import com.jtchen.util.loadUtil;
import org.apache.log4j.Logger;

import java.net.ProtocolException;
import java.nio.FloatBuffer;
import java.util.Properties;


/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/31 22:09
 */
public class CoinTask implements Task {

    private static final Logger logger = Logger.getLogger(CoinTask.class);
    private static final Properties URLProperties = loadUtil.getURLProperties();
    private final User user;
    private final Config config;

    public CoinTask(User user, Config config) {
        this.user = user;
        this.config = config;
    }

    @Override
    public void run() {
        throwCoin("801437152");
    }

    private void throwCoin(String aid) {
        var data = new JSONObject();
        data.put("aid", aid);
        data.put("multiply", config.getMultiply());
        data.put("select_like", config.getSelectLike());
        data.put("cross_domain", "true");
        data.put("csrf", user.getCookie().getBili_jct());

        try {
            Request.post(URLProperties.getProperty("throw-coin"), data);
        } catch (ProtocolException e) {
            logger.error("给视频 aid = " + aid + " 投币异常");
        }
        logger.info("✔ 『投币』给视频 av" + aid + " 投了 " + config.getMultiply() + "个硬币");
    }
}
