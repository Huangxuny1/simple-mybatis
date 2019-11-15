package com.huangxunyi.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Log log = new Log("id-", "create_time-", "ip-", "message-", "success-", "type-", "uid-");
        Object log1 = log;

        Field[] declaredFields = log1.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            //declaredFields[i].setAccessible(true);
            System.out.println(declaredFields[i].getName());
//            Object o = declaredFields[i].get(log1);
//            System.out.println(o);
            String name = declaredFields[i].getName().substring(0, 1).toUpperCase() + declaredFields[i].getName().substring(1);
            Method m1 = log1.getClass().getDeclaredMethod("get" + name);
            Object invoke = m1.invoke(log1);
            System.out.println(invoke);

        }
    }
}
