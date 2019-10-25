package session;

import reflect.ReflectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SimpleResultSetsHandler implements ResultSetsHandler {
    @Override
    public <E> List<E> handleResultSets(Statement stmt, String resultType) throws SQLException {
        List<E> result = new ArrayList<>();
        ResultSet rs = stmt.getResultSet();
        try {
            Object entity = Class.forName(resultType);
            ReflectionUtils.setPropToBeanFromResultSet(entity, rs);
            result.add((E) entity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
