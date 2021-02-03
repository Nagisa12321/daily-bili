package com.jtchen.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtchen.domain.Config;
import com.jtchen.domain.User;
import com.jtchen.domain.Video;
import com.jtchen.task.impl.Task;
import com.jtchen.util.Request;
import com.jtchen.util.loadUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
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

    }

    // 向某个视频投币
    private void throwCoin(Video video) {
        var data = new JSONObject();
        data.put("aid", video.getAid());
        data.put("multiply", config.getMultiply());
        data.put("select_like", config.getSelectLike());
        data.put("cross_domain", "true");
        data.put("csrf", user.getCookie().getBili_jct());

        Request.post(URLProperties.getProperty("throw-coin"), data);

        logger.info("✔ 『投币』给『" + video.getName() + "』的视频 『av" + video.getAid() + "』投了 " + config.getMultiply() + "个硬币");
    }

    // 获取20条推荐视频(未投币)
    private List<Video> getDynamicVideos() {
        ArrayList<Video> videos = new ArrayList<>(20);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", user.getUid());
        jsonObject.put("type_list", 8);

        JSONObject res = Request.get(URLProperties.getProperty("Dynamic-Videos"), jsonObject);
        if (res == null) {
            logger.error("获取推荐视频出现问题");
            return videos;
        }
        JSONObject data = res.getJSONObject("data");
        JSONArray cards = data.getJSONArray("cards");
        for (int i = 0; i < 20; i++) {
            JSONObject card = cards.getJSONObject(i);
            JSONObject desc = card.getJSONObject("desc");
            JSONObject user_profile = desc.getJSONObject("user_profile");
            JSONObject info = user_profile.getJSONObject("info");

            int uid = info.getIntValue("uid");
            String uname = info.getString("uname");
            int aid = desc.getIntValue("rid_str");

            Video video = new Video(aid, uid, uname);

            int coins = video.getAvCoins();
            if (coins == 0)
                videos.add(video);
        }

        return videos;
    }

    // 获取今日已经投的硬币数目
    private int getReward() {
        JSONObject reward = Request.get(URLProperties.getProperty("reward"));
        JSONObject data = reward.getJSONObject("data");


    }
}
