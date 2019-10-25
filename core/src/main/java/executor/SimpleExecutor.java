package executor;

import mapping.MapperStatement;
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
    public <E> List<E> query(MapperStatement mapperStatement, Object params) throws SQLException {
        Statement stmt = null;
        try {
            Connection connection = dataSource.getConnection();
            StatementHandler statementHandler = new SimpleStatementHandler(mapperStatement, resultSetsHandler);
            stmt = statementHandler.prepare(connection);
            return statementHandler.query(stmt, resultSetsHandler);
        } finally {
            closeStmt(stmt);
        }


    }
}