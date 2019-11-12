package transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public interface TransactionFactory {

    /**
     * Sets transaction factory custom properties.
     *
     * @param props
     */
    //设置属性
    void setProperties(Properties props);

    /**
     * Creates a {@link Transaction} out of an existing connection.
     *
     * @param conn Existing database connection
     * @return Transaction
     * @since 3.1.0
     */
    //根据Connection创建Transaction
    Transaction newTransaction(Connection conn);
}