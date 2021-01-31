package com.jtchen.domain;

import com.jtchen.util.loadUtil;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/28 23:20
 */
public class Config {
    private String selectLike;
    private String multiply;

    public Config() {}

    public String getSelectLike() {
        return selectLike;
    }

    public void setSelectLike(String selectLike) {
        this.selectLike = selectLike;
    }

    public String getMultiply() {
        return multiply;
    }

    public void setMultiply(String multiply) {
        this.multiply = multiply;
    }

    public static Config getInstance(){
        var con = new Config();
        var proc = loadUtil.getTaskProperties();
        con.setMultiply(proc.getProperty("multiply"));
        con.setSelectLike(proc.getProperty("selectLike"));

        return con;
    }
}
