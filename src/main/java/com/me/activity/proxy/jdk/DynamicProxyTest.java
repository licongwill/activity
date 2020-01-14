package com.me.activity.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: activity
 * @description:
 * @author: lic
 * @create: 2020-01-14 10:34
 **/
public class DynamicProxyTest {
    private static void saveProxyFile(String... filePath){
        if (filePath.length == 0) {
            System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        }else {
            FileOutputStream outputStream = null;
            try {
                byte[] $Proxy0s = ProxyGenerator.generateProxyClass("$Proxy0", UserDaoImpl.class.getInterfaces());
                File file = new File(filePath[0]);
                if(!file.exists()){
                    file.createNewFile();
                }
                outputStream = new FileOutputStream(file);
                outputStream.write($Proxy0s);

            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (outputStream != null) {
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        saveProxyFile("D:\\cas\\proxy.class");
        UserDao userDao = new UserDaoImpl();
        UserDao instance = new MyInvocationHandler().getInstance(userDao);
        instance.save();
    }
}
