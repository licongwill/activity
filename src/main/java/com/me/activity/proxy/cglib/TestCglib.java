package com.me.activity.proxy.cglib;

import com.me.activity.proxy.jdk.UserDao;
import com.me.activity.proxy.jdk.UserDaoImpl;

/**
 * @program: activity
 * @description:
 * @author: lic
 * @create: 2020-01-14 11:09
 **/
public class TestCglib {
    public static void main(String[] args) {
        UserDao instance = (UserDao)new CglibInvocationHandler().getInstance(UserDaoImpl.class);
        instance.save();
    }
}
