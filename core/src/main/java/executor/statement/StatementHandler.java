package executor.statement;


import executor.resultset.ResultSetsHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler {

    //准备语句
    Statement prepare(Connection connection)
            throws SQLException;

    //参数化
    void parameterize(PreparedStatement statement)
            throws SQLException;

    //批处理
    void batch(Statement statement)
            throws SQLException;

    //update
    int update(Statement statement)
            throws SQLException;

    //select-->结果给ResultHandler
    <E> List<E> query(Statement statement, ResultSetsHandler resultSetsHandler)
            throws SQLException;

}