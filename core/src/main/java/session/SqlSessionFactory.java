package session;

import javax.security.auth.login.Configuration;
import java.sql.Connection;

public interface SqlSessionFactory {

    //8个方法可以用来创建SqlSession实例
    SqlSession openSession();

    //自动提交
    SqlSession openSession(boolean autoCommit);

    //连接
    SqlSession openSession(Connection connection);
    //事务隔离级别
    //SqlSession openSession(TransactionIsolationLevel level);

    //执行器的类型
    SqlSession openSession(ExecutorType execType);

    SqlSession openSession(ExecutorType execType, boolean autoCommit);

    // SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);
    SqlSession openSession(ExecutorType execType, Connection connection);

    Configuration getConfiguration();

}