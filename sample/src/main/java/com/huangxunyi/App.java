package com.huangxunyi;

import session.SqlSession;
import session.SqlSessionFactory;
import session.SqlSessionFactoryBuilder;

import java.util.List;

public class App {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build("config.properties");
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        TestMapper mapper = sqlSession.getMapper(TestMapper.class);

        // select List
        List<Log> all = mapper.getAll();
        System.out.println(" getAll() " + all);
        // select
        Log log = mapper.getLog(all.get(0).getId());
        System.out.println(" getLog() " + log);
        // insert
        int i = mapper.insertLog(new Log("id-", "create_time-", "ip-", "message-", "success-", "type-", "uid-"));
        sqlSession.commit();
        System.out.println(" effect " + i + " " + mapper.getAll().size());

        // update
        Log logToUpdate = mapper.getLog("id-");
        logToUpdate.setMessage("Hello World");
        int i1 = mapper.updateLog(logToUpdate);
        sqlSession.commit();
        System.out.println(" effect " + i1);
        // delete
        int i2 = mapper.deleteLog(logToUpdate);
        sqlSession.commit();
        System.out.println(" effect " + i2);

    }
}
