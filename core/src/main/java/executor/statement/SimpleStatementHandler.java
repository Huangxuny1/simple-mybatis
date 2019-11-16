package executor.statement;

import mapping.MappedStatement;
import executor.resultset.ResultSetsHandler;
import reflect.ReflectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleStatementHandler implements StatementHandler {

    private MappedStatement mappedStatement;
    private ResultSetsHandler resultHandler;
    private String sql;

    private Pattern preparePattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");


    public SimpleStatementHandler(MappedStatement mappedStatement, ResultSetsHandler resultSetsHandler) {
        this.mappedStatement = mappedStatement;
        this.resultHandler = resultSetsHandler;
        this.sql = mappedStatement.getSql();
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        // todo check SQL
        mappedStatement.setOriginSql(this.sql);
        replaceWithQuestionMark(this.sql);
        Statement statement = connection.prepareStatement(sql);
        // todo config
        return statement;
    }

    @Override
    public void parameterize(PreparedStatement statement) throws SQLException {
        //todo Just Test  需要反射 #{} 中的字段 并获取 传入对象对应的field  并获得值 根据类型 set
        Object obj = mappedStatement.getObj();
        List<String> params = mappedStatement.getParams();
        if (params.size() == 1) {
            String typeName = obj.getClass().getTypeName();
            if (String.class.getTypeName().equals(typeName)) {
                statement.setString(1, (String) obj);
            } else if ("int".equals(typeName) || Integer.class.getTypeName().equals(typeName)) {
                statement.setInt(1, (int) obj);
            }
        } else if (params.size() > 1) {
            for (int i = 0; i < params.size(); i++) {
                ReflectionUtils.setParams(i, statement, obj, params.get(i));
            }
        }
    }


    @Override
    public void batch(Statement statement) throws SQLException {
        statement.addBatch(sql);
    }


    @Override
    public int update(Statement statement) throws SQLException {
        // KeyGen
        PreparedStatement statement1 = (PreparedStatement) statement;
        statement1.execute();
        return statement1.getUpdateCount();
    }

    @Override
    public int delete(Statement statement) throws SQLException {
        PreparedStatement statement1 = (PreparedStatement) statement;
        statement1.execute();
        return statement1.getUpdateCount();
    }

    @Override
    public <E> List<E> query(Statement statement, ResultSetsHandler resultSetsHandler) throws SQLException {
        PreparedStatement statement1 = (PreparedStatement) statement;
        statement1.execute();
        return resultSetsHandler.handleResultSets(statement1, mappedStatement.getResultType());
    }

    private void replaceWithQuestionMark(String source) {
        source = source.trim();
        Matcher matcher = preparePattern.matcher(source);
        List<String> params = new ArrayList<>(matcher.groupCount());
        while (matcher.find()) {
            String group = matcher.group();
            params.add(group.substring(2, group.length() - 1));
        }
        mappedStatement.setParams(params);
        String sql = matcher.replaceAll("?");
        this.sql = sql;
    }
}
