package com.jtchen.domain;

import org.apache.http.NameValuePair;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/2/1 0:10
 */
public class Pair implements NameValuePair {

    private final String name;
    private final String val;

    public Pair(String name, String val) {
        this.name = name;
        this.val = val;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return val;
    }
}
