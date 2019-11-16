package session.impl;

import executor.Executor;
import mapping.MappedStatement;
import session.Configuration;
import executor.resultset.ResultSetsHandler;
import session.SqlSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SimpleSqlSession implements SqlSession {
    private final Configuration configuration;
    private final Executor executor;

    public SimpleSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }


    @Override
    public <T> T selectOne(String statement) throws SQLException {
        List<T> ts = this.selectList(statement);
        return ts == null ? null : ts.get(0);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) throws SQLException {
        List<T> ts = this.selectList(statement, parameter);
        return ts == null ? null : ts.get(0);
    }

    @Override
    public <E> List<E> selectList(String statement) throws SQLException {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        if (null == mappedStatement) {
            throw new RuntimeException(" mappedStatement is null , ID : " + statement);
        }
        return executor.query(mappedStatement, null);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) throws SQLException {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        if (null == mappedStatement) {
            throw new RuntimeException(" mappedStatement is null , ID : " + statement);
        }
        return executor.query(mappedStatement, parameter);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return null;
    }

    @Override
    public void select(String statement, Object parameter, ResultSetsHandler handler) {

    }

    @Override
    public void select(String statement, ResultSetsHandler handler) {

    }

    @Override
    public int insert(String statement) {
        return 0;
    }

    @Override
    public int insert(String statement, Object parameter) {
        return 0;
    }

    @Override
    public int update(String statement, Object parameter) {
        try {
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);
            return executor.update(mappedStatement, parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int delete(String statement) {
        return 0;
    }

    @Override
    public int delete(String statement, Object parameter) {
        return 0;
    }

    @Override
    public void commit() {
        try {
            executor.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commit(boolean force) {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void rollback(boolean force) {

    }

    @Override
    public void close() {

    }

    @Override
    public void clearCache() {

    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
