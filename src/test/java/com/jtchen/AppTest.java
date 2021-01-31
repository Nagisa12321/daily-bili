package com.jtchen;

import org.junit.Test;

import static com.jtchen.util.CheckUtil.checkEnv;
import static com.jtchen.util.CheckUtil.checkUser;


public class AppTest {

    @Test
    public void CheckTest() {
        var user = checkEnv();
        System.out.println(checkUser(user));

        System.out.println(user);
    }
}