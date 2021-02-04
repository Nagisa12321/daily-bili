package com.jtchen.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    private static final Properties TaskProperties = loadUtil.getTaskProperties();
    private final User user;

    public CoinTask(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        // 今日已投
        int todayCoin = getReward();
        logger.info("✔ 获取你的今日投币: 今天你一共投了 " + todayCoin + " 个硬币");
        List<Video> videos = getDynamicVideos();
        // 设置中每日投币数
        int coinToThrow = Integer.parseInt(TaskProperties.getProperty("coins-to-throw"));
        int myCoin = Integer.parseInt(user.getMoney());
        int num = Math.min(myCoin, coinToThrow - todayCoin);
        logger.info("✔ 经过计算你今天还需投 " + num + " 个硬币");
        int idx = 0;
        while (num != 0) {
            Video video = videos.get(idx++);
            if (throwCoin(video)) num--;
            if (idx >= 20) break;
        }
        logger.info("✔ 投币任务执行完成");
    }

    // 向某个视频投币
    private boolean throwCoin(Video video) {
        var data = new JSONObject();
        data.put("aid", video.getAid());
        data.put("multiply", TaskProperties.getProperty("multiply"));
        data.put("select_like", TaskProperties.getProperty("selectLike"));
        data.put("cross_domain", "true");
        data.put("csrf", user.getCookie().getBili_jct());

        JSONObject post = Request.post(URLProperties.getProperty("throw-coin"), data);

        if (post == null || post.getInteger("code") == -111) {
            if (post == null) logger.warn("❌ 『投币』失败, 原因: 获取不到post的JSON");
            else logger.warn("❌ 『投币』失败, 原因: csrf校验失败");
            return false;
        }

        logger.info("✔ 『投币』给『" + video.getName() + "』的视频 『av" + video.getAid() + "』投了 " + TaskProperties.getProperty("multiply") + "个硬币");
        return true;
    }

    // 获取20条推荐视频(未投币)
    private List<Video> getDynamicVideos() {
        ArrayList<Video> videos = new ArrayList<>(20);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", user.getUid());
        jsonObject.put("type_list", "8");

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

            String uid = info.getString("uid");
            String uname = info.getString("uname");
            String aid = desc.getString("rid_str");

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
        return data.getInteger("coins_av") / 10;
    }
}
