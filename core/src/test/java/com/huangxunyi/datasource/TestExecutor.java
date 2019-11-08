package com.huangxunyi.datasource;

import datasource.SimpleDataSource;
import executor.Executor;
import executor.SimpleExecutor;
import mapping.MapperStatement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import session.ResultSetsHandler;
import session.SimpleResultSetsHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class TestExecutor {
    private DataSource ds;
    private Executor e;

    @Before
    public void dataSource() {
        ds = new SimpleDataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT", "root", "root");
        Assert.assertNotNull(ds);
        ResultSetsHandler rsh = new SimpleResultSetsHandler();
        e = new SimpleExecutor(ds, rsh);
        Assert.assertNotNull(e);
    }

    @Test
    public void testSimpleExecutor() throws SQLException {
        MapperStatement mapperStatement = new MapperStatement("1", "2", "com.huangxunyi.datasource.Log", "select * from sys_log_login");
        List<Log> query = e.query(mapperStatement, null);
        for (int i = 0; i < query.size(); i++) {
            Log log = query.get(i);
            System.out.println(log);
        }
        //  int update = e.update(new MapperStatement("1", "2", "config.Log", "update sys_log_login Set create_time='2018-07-06 14:44:22' where id='402880e4646e54dc01646e555e950000'"), null);
        //  int insert = e.update(new MapperStatement("1", "2", "config.Log", "insert into sys_log_login values ('402880e4646e54dc01646e555e950001','2018-07-06 14:44:06','192.168.0.100','权限认证成功','成功','1','402880e4641c692001641c6be3910002')"), null);
        //  int delete = e.update(new MapperStatement("1", "2", "config.Log", "delete from sys_log_login where id='402880e4646e54dc01646e555e950000'"), null);
        //System.out.println(delete);
    }
}
