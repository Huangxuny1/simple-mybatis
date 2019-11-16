package session;


import executor.resultset.ResultSetsHandler;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SqlSession extends Closeable {


    /**
     * Retrieve a single row mapped from the executor.statement key and parameter.
     * 根据指定的SqlID获取一条记录的封装对象，只不过这个方法容许我们可以给sql传递一些参数
     * 一般在实际使用中，这个参数传递的是pojo，或者Map或者ImmutableMap
     *
     * @param <T>       the returned object type
     * @param statement Unique identifier matching the executor.statement to use.
     * @param parameter A parameter object to pass to the executor.statement.
     * @return Mapped object
     */
    <T> T selectOne(String statement, Object parameter) throws SQLException;

    /**
     * Retrieve a list of mapped objects from the executor.statement key and parameter.
     * 根据指定的sqlId获取多条记录
     *
     * @param <E>       the returned list element type
     * @param statement Unique identifier matching the executor.statement to use.
     * @return List of mapped object
     */
    <E> List<E> selectList(String statement) throws SQLException;


    /**
     * Retrieve a list of mapped objects from the executor.statement key and parameter,
     * within the specified row bounds.
     * 获取多条记录，这个方法容许我们可以传递一些参数，不过这个方法容许我们进行
     * 分页查询。
     * <p>
     * 需要注意的是默认情况下，Mybatis为了扩展性，仅仅支持内存分页。也就是会先把
     * 所有的数据查询出来以后，然后在内存中进行分页。因此在实际的情况中，需要注意
     * 这一点。
     * <p>
     * 一般情况下公司都会编写自己的Mybatis 物理分页插件
     *
     * @param <E>       the returned list element type
     * @param statement Unique identifier matching the executor.statement to use.
     * @param parameter A parameter object to pass to the executor.statement.
     * @return List of mapped object
     */
    <E> List<E> selectList(String statement, Object parameter) throws SQLException;

    /**
     * The selectMap is a special case in that it is designed to convert a list
     * of results into a Map based on one of the properties in the resulting
     * objects.
     * Eg. Return a of Map[Integer,Author] for selectMap("selectAuthors","id")
     * 将查询到的结果列表转换为Map类型。
     *
     * @param <K>       the returned Map keys type
     * @param <V>       the returned Map values type
     * @param statement Unique identifier matching the executor.statement to use.
     * @param mapKey    The property to use as key for each value in the list. 这个参数会作为结果map的key
     * @return Map containing key pair data.
     */
    <K, V> Map<K, V> selectMap(String statement, String mapKey);


    /**
     * The selectMap is a special case in that it is designed to convert a list
     * of results into a Map based on one of the properties in the resulting
     * objects.
     * 获取多条记录,加上分页,并存入Map
     *
     * @param <K>       the returned Map keys type
     * @param <V>       the returned Map values type
     * @param statement Unique identifier matching the executor.statement to use.
     * @param parameter A parameter object to pass to the executor.statement.
     * @param mapKey    The property to use as key for each value in the list.
     * @return Map containing key pair data.
     */
    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);

    /**
     * Retrieve a single row mapped from the executor.statement key and parameter
     * using a {@code ResultHandler}.
     *
     * @param statement Unique identifier matching the executor.statement to use.
     * @param parameter A parameter object to pass to the executor.statement.
     * @param handler   ResultHandler that will handle each retrieved row
     * @return Mapped object
     */
    void select(String statement, Object parameter, ResultSetsHandler handler);

    /**
     * Retrieve a single row mapped from the executor.statement
     * using a {@code ResultHandler}.
     * 获取一条记录,并转交给ResultHandler处理。这个方法容许我们自己定义对
     * 查询到的行的处理方式。不过一般用的并不是很多
     *
     * @param statement Unique identifier matching the executor.statement to use.
     * @param handler   ResultHandler that will handle each retrieved row
     * @return Mapped object
     */
    void select(String statement, ResultSetsHandler handler);


    /**
     * Execute an insert executor.statement with the given parameter object. Any generated
     * autoincrement values or selectKey entries will modify the given parameter
     * object properties. Only the number of rows affected will be returned.
     * 插入记录，容许传入参数。
     *
     * @param statement Unique identifier matching the executor.statement to execute.
     * @param parameter A parameter object to pass to the executor.statement.
     * @return int The number of rows affected by the insert. 注意返回的是受影响的行数
     */
    int insert(String statement, Object parameter);

    /**
     * Execute an update executor.statement. The number of rows affected will be returned.
     * 更新记录
     *
     * @param statement Unique identifier matching the executor.statement to execute.
     * @param parameter A parameter object to pass to the executor.statement.
     * @return int The number of rows affected by the update. 返回的是受影响的行数
     */
    int update(String statement, Object parameter);


    /**
     * Execute a delete executor.statement. The number of rows affected will be returned.
     * 删除记录
     *
     * @param statement Unique identifier matching the executor.statement to execute.
     * @param parameter A parameter object to pass to the executor.statement.
     * @return int The number of rows affected by the delete. 返回的是受影响的行数
     */
    int delete(String statement, Object parameter);

    //以下是事务控制方法,commit,rollback

    /**
     * Flushes batch statements and commits database connection.
     * Note that database connection will not be committed if no updates/deletes/inserts were called.
     */
    void commit();


    /**
     * Discards pending batch statements and rolls database connection back.
     * Note that database connection will not be rolled back if no updates/deletes/inserts were called.
     */
    void rollback();



//  /**
//   * Flushes batch statements.
//   * 刷新批处理语句,返回批处理结果
//   * @return BatchResult list of updated records
//   */
//  List<BatchResult> flushStatements();

    /**
     * Closes the session
     * 关闭Session
     */
    @Override
    void close();

    /**
     * Clears local session cache
     * 清理Session缓存
     */
    void clearCache();

    //  /**
//   * Retrieves current configuration
//   * 得到配置
//   * @return Configuration
//   */
    Configuration getConfiguration();

    /**
     * Retrieves a mapper.
     * 得到映射器
     * 这个巧妙的使用了泛型，使得类型安全
     * 到了MyBatis 3，还可以用注解,这样xml都不用写了
     *
     * @param <T>  the mapper type
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     */
    <T> T getMapper(Class<T> type);

    /**
     * Retrieves inner database connection
     * 得到数据库连接
     *
     * @return Connection
     */
    Connection getConnection();
}