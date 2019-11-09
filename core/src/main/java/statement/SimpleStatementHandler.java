package statement;

import mapping.MappedStatement;
import session.ResultSetsHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SimpleStatementHandler implements StatementHandler {

    private MappedStatement mappedStatement;
    private ResultSetsHandler resultHandler;
    private String sql;


    public SimpleStatementHandler(MappedStatement mappedStatement, ResultSetsHandler resultSetsHandler) {
        this.mappedStatement = mappedStatement;
        this.resultHandler = resultSetsHandler;
        this.sql = mappedStatement.getSql();
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        // todo check SQL
        Statement statement = null;
        statement = initStatment(connection);
        // todo config
        return statement;
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {

    }

    @Override
    public void batch(Statement statement) throws SQLException {
        statement.addBatch(sql);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        // KeyGen
        statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
        return statement.getUpdateCount();
    }

    @Override
    public <E> List<E> query(Statement statement, ResultSetsHandler resultSetsHandler) throws SQLException {
        statement.execute(sql);
        return resultSetsHandler.handleResultSets(statement, mappedStatement.getResultType());
    }


    private Statement initStatment(Connection connection) throws SQLException {
        return connection.createStatement();
    }
}
