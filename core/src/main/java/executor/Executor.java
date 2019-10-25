package executor;

import mapping.MapperStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {
    public <E> List<E> query(MapperStatement mapperStatement, Object params) throws SQLException;
}
