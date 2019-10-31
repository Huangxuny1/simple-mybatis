package com.huangxunyi.datasource;

import org.junit.Test;

import java.lang.reflect.Field;

public class TestReflect {
    @Test
    public void ref() {
        try {
            Object o= Class.forName("config.Log").newInstance();
            Field[] fields = o.getClass().getDeclaredFields();
           // Field[] fields = Class.forName("config.Log").getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                System.out.println(fields[i].getType().getSimpleName()+"\t"+fields[i].getName());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
