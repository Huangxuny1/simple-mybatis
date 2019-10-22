package executor;

import config.MapperStatement;

import java.util.List;

public interface Executor {
    <E> List<E> query(MapperStatement mapperStatement,Object params);
}
