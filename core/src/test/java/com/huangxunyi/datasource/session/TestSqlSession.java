package com.huangxunyi.datasource.session;

import com.huangxunyi.datasource.Log;
import org.junit.Test;
import session.SqlSession;
import session.SqlSessionFactory;
import session.SqlSessionFactoryBuilder;

import java.sql.SQLException;
import java.util.List;

public class TestSqlSession {

    @Test
    public void TestSqlSessionFactoryBuilder() throws SQLException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build("config.properties");
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        List<Log> getAll = sqlSession.selectList("com.huangxunyi.datasource.Log.getAll");
        System.out.println(getAll.toString());

    }
}
