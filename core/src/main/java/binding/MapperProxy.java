package binding;

import mapping.MappedStatement;
import session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.Collection;

public class MapperProxy<T> implements Serializable, InvocationHandler {
    private static final long serialVersionUID = 1L;

    private final SqlSession sqlSession;

    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            //代理以后，所有Mapper的方法调用时，都会调用这个invoke方法
            //并不是任何一个方法都需要执行调用代理对象进行执行，如果这个方法是Object中通用的方法（toString、hashCode等）无需执行
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }
            return execute(method, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object execute(Method method, Object[] args) throws SQLException {
        String stmtID = mapperInterface.getName() + "." + method.getName();
        MappedStatement ms = sqlSession.getConfiguration().getMappedStatement(stmtID);
        Object result = null;

        switch (ms.getSqlType()) {
            case SELECT:
                Class<?> returnType = method.getReturnType();
                //返回list
                if (Collection.class.isAssignableFrom(returnType)) {
                    result = sqlSession.selectList(stmtID, args);
                } else {
                    // 返回一个对象
                    result = sqlSession.selectOne(stmtID, args);
                }
                break;

            case UPDATE:
                sqlSession.update(stmtID, args);
                break;
            case INSERT:
                sqlSession.insert(stmtID, args);
                break;
            default:
                //todo defailt
                break;
        }
        return result;
    }
}
