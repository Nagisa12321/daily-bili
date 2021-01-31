package com.jtchen.util;

import com.alibaba.fastjson.JSONObject;
import com.jtchen.domain.User;
import org.apache.log4j.Logger;

import java.net.ProtocolException;
import java.util.Properties;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/30 23:16
 */
public class CheckUtil {
    public static Logger logger = Logger.getLogger(CheckUtil.class);
    public static Properties URLProperties = loadUtil.getURLProperties();

    public static User checkEnv() {
        String bili_jct = System.getenv("BILI_JCT");
        String SESSDATA = System.getenv("SESSDATA");
        String DedeUserID = System.getenv("DedeUserID");
        String DedeUserID__ckMd5 = System.getenv("DedeUserID__ckMd5");

        if (bili_jct == null || SESSDATA == null || DedeUserID == null)
            return null;

        User user = new User(DedeUserID, bili_jct, SESSDATA, DedeUserID__ckMd5);
        Request.setCookie(user.getCookie());
        return user;
    }

    public static boolean checkUser(User user) {
        try {
            var USERObj = Request.get(URLProperties.getProperty("User-Info"));
            if (USERObj == null) return false;
            var data = USERObj.getJSONObject("data");
            /* 用户名 */
            user.setUname((String) data.get("uname"));

            /* 账户的uid */
            user.setUid(String.valueOf(data.get("mid")));

            /* vip类型 */
            user.setVipType(String.valueOf(data.get("vipType")));

            /* 硬币数 */
            user.setMoney(String.valueOf(data.get("money")));

            /* 经验 */
            JSONObject level_info = data.getJSONObject("level_info");
            user.setCurrentExp(String.valueOf(level_info.get("current_exp")));

            /* 大会员状态 */
            JSONObject vip_label = data.getJSONObject("vip_label");
            user.setVipStatus((String) (vip_label.get("text")));

            /* 升级到下一级所需要的经验 */
            user.setNextExp(String.valueOf(level_info.get("next_exp")));

            /* 获取当前的等级 */
            user.setCurrentLevel(String.valueOf(level_info.get("current_level")));

        } catch (ProtocolException e) {
            logger.error(e);
            return false;
        }
        return true;
    }
}
