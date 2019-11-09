package binding;

import session.SqlSession;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {
    private final Map<Class<?>, MapperProxyFactory<?>> mappers = new HashMap<>();

    public <T> void addMapper(Class<T> type) {
        mappers.put(type, new MapperProxyFactory<T>(type));
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) mappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
    }
}
