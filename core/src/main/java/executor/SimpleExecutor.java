package executor;

import executor.resultset.ResultSetsHandler;
import executor.resultset.SimpleResultSetsHandler;
import executor.statement.SimpleStatementHandler;
import executor.statement.StatementHandler;
import mapping.MappedStatement;
import session.Configuration;
import transaction.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SimpleExecutor implements Executor {
    private Configuration configuration;
    private Transaction transaction;
    private ResultSetsHandler resultSetsHandler;

    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
        resultSetsHandler = new SimpleResultSetsHandler();
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object params) throws SQLException {
        Statement stmt = null;
        try {
            StatementHandler statementHandler = new SimpleStatementHandler(mappedStatement, resultSetsHandler);
            Connection connection = transaction.getConnection();
            stmt = statementHandler.prepare(connection);
            return statementHandler.query(stmt, resultSetsHandler);
        } finally {
            closeStmt(stmt);
        }
    }

    @Override
    public int insert(MappedStatement ms, Object parameter) throws SQLException {
        PreparedStatement stmt = null;
        try {
            Connection connection =transaction.getConnection();
            ms.setObj(parameter);
            StatementHandler statementHandler = new SimpleStatementHandler(ms, resultSetsHandler);
            stmt = (PreparedStatement) statementHandler.prepare(connection);
            statementHandler.parameterize(stmt);
            return statementHandler.update(stmt);
        }finally {
            closeStmt(stmt);
        }
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        PreparedStatement stmt = null;
        try {
            Connection connection = transaction.getConnection();
            ms.setObj(parameter);
            StatementHandler statementHandler = new SimpleStatementHandler(ms, resultSetsHandler);
            stmt = (PreparedStatement) statementHandler.prepare(connection);
            statementHandler.parameterize(stmt);

            return statementHandler.update(stmt);
        } finally {
            closeStmt(stmt);
        }
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

    public void commit() throws SQLException {
        transaction.commit();
    }
}
