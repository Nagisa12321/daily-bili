package com.jtchen.util;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/30 0:23
 */
public class Cookie {
    private final String DedeUserID;
    private final String bili_jct;
    private final String SESSDATA;
    private final String DedeUserID__ckMd5;

    public Cookie(String dedeUserID, String bili_jct, String SESSDATA, String DedeUserID__ckMd5) {
        this.DedeUserID__ckMd5 = DedeUserID__ckMd5;
        this.DedeUserID = dedeUserID;
        this.bili_jct = bili_jct;
        this.SESSDATA = SESSDATA;
    }

    public String toString() {
        return "DedeUserID=" + DedeUserID
                + "; SESSDATA=" + SESSDATA
                + "; bili_jct=" + bili_jct
                + "; DedeUserID__ckMd5=" + DedeUserID__ckMd5 + ";";
    }

}
