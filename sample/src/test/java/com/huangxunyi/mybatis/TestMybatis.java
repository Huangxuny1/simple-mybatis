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
        List<Log> allLog = session.selectList("getAllLog");
        Log log = allLog.get(0);
//        log.setMessage("change Message For Update");
//        session.update("updateLog");
//        System.out.println(getAllLog);
//        Map<String, Log> LogMap = session.selectMap("getAllLog", "create_time");
//        System.out.println(LogMap);
        LogMapper mapper = session.getMapper(LogMapper.class);
        log.setMessage(" message1 ");
//        System.out.println(log);
        mapper.updateLog(log);
//        log.setMessage(" insert message ");
//        log.setId("402880e4643a734301643a7403d4211");
//        mapper.insertLog(log);
        session.commit();
        session.close();
    }
}