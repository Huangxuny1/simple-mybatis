package com.huangxunyi.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class TestMybatis {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

//        Log log=  session.selectOne("getLogById",2);
//        System.out.println(log.toString());
//        List<Log> getAllLog = session.selectList("getAllLog");
//        System.out.println(getAllLog);
        Map<String, Log> LogMap = session.selectMap("getAllLog", "create_time");
        System.out.println(LogMap);
        //session.commit();
        session.close();
    }
}