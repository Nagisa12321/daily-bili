package com.jtchen.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;

import java.net.HttpURLConnection;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/31 23:15
 */
public class ParsingUtil {

    public static NameValuePair[] getPairList(JSONObject pJson) {
        Pair[] pairs = new Pair[pJson.size()];
        int idx = 0;
        for (var entry : pJson.entrySet())
            pairs[idx++] = new Pair(entry.getKey(), val2String(entry.getValue()));
        return pairs;
    }

    public static String val2String(Object val) {
        return val == null ? "" : (String) val;
    }
}
