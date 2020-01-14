package com.me.activity.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: activity
 * @description:
 * @author: lic
 * @create: 2020-01-14 11:07
 **/
public class CglibInvocationHandler implements MethodInterceptor {
    public Object getInstance(Class clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib begin");
        methodProxy.invokeSuper(o, objects);
        System.out.println("cglib end");
        return null;
    }
}
