package com.huangxunyi.datasource;

import net.sf.cglib.proxy.Callback;
import org.junit.Test;

import java.lang.reflect.Field;

public class TestReflect {
    @Test
    public void ref() {
        try {
            Object o= Class.forName("com.huangxunyi.datasource.Log").newInstance();
            Field[] fields = o.getClass().getDeclaredFields();
           // Field[] fields = Class.forName("com.huangxunyi.datasource.Log").getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                System.out.println(fields[i].getType().getSimpleName()+"\t"+fields[i].getName());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
