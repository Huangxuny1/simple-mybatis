package transaction.impl;

import transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCTransaction implements Transaction {

    private DataSource dataSource;


    //todo 暂不处理 事务隔离级别 和 自动提交
    private int level;
    private boolean autoCommit;

    private Connection connection;

    public JDBCTransaction(DataSource ds, int level, boolean autoCommit) {
        this.dataSource = ds;
        this.level = level;
        this.autoCommit = autoCommit;
    }

    @Override
    public Connection getConnection() throws SQLException {
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        if (null != connection) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (null != connection) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (null != connection) {
            connection.close();
        }
    }
}
