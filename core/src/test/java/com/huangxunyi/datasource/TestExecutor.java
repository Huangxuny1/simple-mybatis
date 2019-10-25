package com.huangxunyi.datasource;

import datasource.SimpleDataSource;
import executor.Executor;
import executor.SimpleExecutor;
import mapping.MapperStatement;
import org.junit.Test;
import session.ResultSetsHandler;
import session.SimpleResultSetsHandler;
import statement.SimpleStatementHandler;
import statement.StatementHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class TestExecutor {
    @Test
    public void testSimpleExecutor() throws SQLException {
        DataSource ds = new SimpleDataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT", "root", "root");
        ResultSetsHandler rsh = new SimpleResultSetsHandler();
        Executor e = new SimpleExecutor(ds, rsh);

        MapperStatement mapperStatement = new MapperStatement("1", "2", "1", "select * from sys_log_login");
        List<Object> query = e.query(mapperStatement, null);
    }
}
