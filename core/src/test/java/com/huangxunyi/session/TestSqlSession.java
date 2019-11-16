package com.huangxunyi.session;

import com.huangxunyi.Log;
import com.huangxunyi.mapper.TestMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import session.SqlSession;
import session.SqlSessionFactory;
import session.SqlSessionFactoryBuilder;

import java.sql.SQLException;
import java.util.List;

public class TestSqlSession {
    private SqlSession sqlSession;
    private TestMapper mapper;

    @Before
    public void OpenSqlSessionByConfig() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build("config.properties");
        sqlSession = sqlSessionFactory.openSession(false);
        mapper = sqlSession.getMapper(TestMapper.class);
    }

    @Test
    public void TestSessionGetAllByStmtID() throws SQLException {

        List<Log> getAll = sqlSession.selectList("getAll");
        System.out.println(getAll.toString());

    }

    @Test
    public void TestSessionGetAllByMapper() {
        List<Log> all = mapper.getAll();
        System.out.println(all.toString());
    }

    @Test
    public void TestUpdate() {
        List<Log> all = mapper.getAll();
        Log log = all.get(0);
        log.setMessage("Message");
        System.out.println(mapper.updateLog(log));
        sqlSession.commit();
    }

    @Test
    public void TestInsert(){
        Log log = new Log("id-", "create_time-", "ip-", "message-", "success-", "type-", "uid-");
        System.out.println(mapper.insertLog(log));
        sqlSession.commit();
    }

    @After
    public void Destroy() {
        sqlSession.close();
    }
}
