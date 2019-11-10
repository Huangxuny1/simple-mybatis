package executor.resultset;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ResultSetsHandler {
    //处理结果集
    <E> List<E> handleResultSets(Statement stmt,String resultType) throws SQLException;

}
