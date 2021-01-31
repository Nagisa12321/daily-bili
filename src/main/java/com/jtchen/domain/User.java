package com.jtchen.domain;

import com.jtchen.util.Cookie;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/28 23:20
 */
public class User {
    private final Cookie cookie;

    /* 用户名 */
    private String uname;
    /* 用户id */
    private String uid;
    /* 大会员类型 */
    private String vipType;
    /* 硬币 */
    private String money;
    /* 当前经验 */
    private String currentExp;
    /* 大会员状态 */
    private String vipStatus;
    /* 当前等级 */
    private String currentLevel;
    /* 下一等级所需经验 */
    private String nextExp;

    public User(String dedeUserID, String bili_jct, String SESSDATA, String DedeUserID__ckMd5) {
        this.cookie = new Cookie(dedeUserID, bili_jct, SESSDATA, DedeUserID__ckMd5);
    }

    public int getRemainingExp() {
        return Integer.parseInt(nextExp) - Integer.parseInt(currentExp);
    }

    public Cookie getCookie() {
        return cookie;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(String currentExp) {
        this.currentExp = currentExp;
    }

    public String getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(String vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getNextExp() {
        return nextExp;
    }

    public void setNextExp(String nextExp) {
        this.nextExp = nextExp;
    }

    @Override
    public String toString() {
        return "User{" +
                "uname='" + uname + '\'' +
                ", uid='" + uid + '\'' +
                ", vipType='" + vipType + '\'' +
                ", money='" + money + '\'' +
                ", currentExp='" + currentExp + '\'' +
                ", vipStatus='" + vipStatus + '\'' +
                ", currentLevel='" + currentLevel + '\'' +
                ", nextExp='" + nextExp + '\'' +
                '}';
    }
}
