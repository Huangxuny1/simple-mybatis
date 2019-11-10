package session.impl;

import session.Configuration;
import session.ExecutorType;
import session.SqlSession;
import session.SqlSessionFactory;

import java.sql.Connection;

public class SimpleSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public SimpleSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return null;
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        return null;
    }

    @Override
    public SqlSession openSession(Connection connection) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType execType) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType execType, Connection connection) {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }
}
