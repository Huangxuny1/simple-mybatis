package com.huangxunyi.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception {
        Log log = new Log("id-", "create_time-", "ip-", "message-", "success-", "type-", "uid-");
        Object log1 = log;
        t(log1);
//        Field id = log1.getClass().getField("id");
//        System.out.println(id.getGenericType().getTypeName());
//        System.out.println(id.get(log1));

    }

    public static void t(Object log1) throws  Exception{
        Field[] declaredFields = log1.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            //declaredFields[i].setAccessible(true);
            System.out.println(declaredFields[i].getName());
//            Object o = declaredFields[i].get(log1);
//            System.out.println(o);
            String name = declaredFields[i].getName().substring(0, 1).toUpperCase() + declaredFields[i].getName().substring(1);
            Method m1 = log1.getClass().getDeclaredMethod("get" + name);
            System.out.println(m1.getReturnType().getName());
            Object invoke = m1.invoke(log1);
            System.out.println(invoke);

        }
    }
}
