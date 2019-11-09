package executor;

import mapping.MappedStatement;
import session.ResultSetsHandler;
import statement.SimpleStatementHandler;
import statement.StatementHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SimpleExecutor implements Executor {
    private DataSource dataSource;
    private ResultSetsHandler resultSetsHandler;

    public SimpleExecutor(DataSource dataSource, ResultSetsHandler resultSetsHandler) {
        this.dataSource = dataSource;
        this.resultSetsHandler = resultSetsHandler;
    }


    private void closeStmt(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object params) throws SQLException {
        Statement stmt = null;
        try {
            Connection connection = dataSource.getConnection();
            StatementHandler statementHandler = new SimpleStatementHandler(mappedStatement, resultSetsHandler);
            stmt = statementHandler.prepare(connection);
            return statementHandler.query(stmt, resultSetsHandler);
        } finally {
            closeStmt(stmt);
        }


    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        Statement stmt = null;
        try {
            Connection connection = dataSource.getConnection();
            StatementHandler statementHandler = new SimpleStatementHandler(ms, resultSetsHandler);
            stmt = statementHandler.prepare(connection);
            return statementHandler.update(stmt);
        } finally {
            closeStmt(stmt);
        }
    }
}
