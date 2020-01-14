package com.me.activity.proxy.jdk;

/**
 * @program: activity
 * @description:
 * @author: lic
 * @create: 2020-01-14 10:33
 **/
public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("save user");
    }
}
