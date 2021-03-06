package executor;

import mapping.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {
    <E> List<E> query(MappedStatement mappedStatement, Object params) throws SQLException;

    int insert(MappedStatement ms, Object parameter) throws SQLException;

    int update(MappedStatement ms, Object parameter) throws SQLException;

    int delete(MappedStatement ms, Object parameter) throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;
}
