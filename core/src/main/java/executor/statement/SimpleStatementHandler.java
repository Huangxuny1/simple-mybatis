package executor.statement;

import mapping.MappedStatement;
import executor.resultset.ResultSetsHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleStatementHandler implements StatementHandler {

    private MappedStatement mappedStatement;
    private ResultSetsHandler resultHandler;
    private String sql;

    private static Pattern preparePattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");


    public SimpleStatementHandler(MappedStatement mappedStatement, ResultSetsHandler resultSetsHandler) {
        this.mappedStatement = mappedStatement;
        this.resultHandler = resultSetsHandler;
        this.sql = mappedStatement.getSql();
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        // todo check SQL
        String sql = parseSymbol(this.sql);
        mappedStatement.setSql(sql);
        Statement statement = connection.prepareStatement(sql);
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
        statement.executeQuery(sql);
        return resultSetsHandler.handleResultSets(statement, mappedStatement.getResultType());
    }

    private static String parseSymbol(String source) {
        source = source.trim();
        Matcher matcher = preparePattern.matcher(source);
        return matcher.replaceAll("?");
    }
}
