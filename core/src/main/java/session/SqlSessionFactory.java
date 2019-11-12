package session;

import java.sql.Connection;

public interface SqlSessionFactory {

    //8个方法可以用来创建SqlSession实例
    SqlSession openSession();

    //自动提交
    SqlSession openSession(boolean autoCommit);

    Configuration getConfiguration();

}