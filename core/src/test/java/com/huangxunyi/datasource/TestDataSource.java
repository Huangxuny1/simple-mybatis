package com.huangxunyi.datasource;

import datasource.SimpleDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Enumeration;

import static org.junit.Assert.assertEquals;

public class TestDataSource {
    @Test
    public void testSimpleDataSource() throws SQLException {
        DataSource dataSource = new SimpleDataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT", "root", "root");
        Connection connection = dataSource.getConnection();
    }

}
