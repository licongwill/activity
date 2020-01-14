package com.me.activity.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: activity
 * @description:
 * @author: lic
 * @create: 2020-01-14 10:58
 **/
public class MyInvocationHandler implements InvocationHandler {

    private UserDao target;

    public UserDao getInstance(UserDao userDao) {
        this.target = userDao;
        Class<? extends UserDao> aClass = userDao.getClass();
        UserDao instance = (UserDao)Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), this);
        return instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理监听begin");
        method.invoke(target,args);
        System.out.println("代理监听end");
        return null;
    }
}
