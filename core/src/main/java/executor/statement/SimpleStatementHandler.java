package executor.statement;

import mapping.MappedStatement;
import executor.resultset.ResultSetsHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        mappedStatement.setOriginSql(this.sql);
        String sql = parseSymbol(this.sql);
        mappedStatement.setSql(sql);
        Statement statement = connection.prepareStatement(sql);
        // todo config
        return statement;
    }

    @Override
    public void parameterize(PreparedStatement statement) throws SQLException {
        //todo Just Test  需要反射 #{} 中的字段 并获取 传入对象对应的field  并获得值 根据类型 set
        statement.setString(1, "asdasd");
        statement.setString(2, "402880e4643a734301643a7403d4111");
    }


    @Override
    public void batch(Statement statement) throws SQLException {
        statement.addBatch(sql);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        // KeyGen
        PreparedStatement statement1 = (PreparedStatement) statement;
        parameterize(statement1);
        statement1.execute();
        return statement1.getUpdateCount();
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
