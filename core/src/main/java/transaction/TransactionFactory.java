package transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public interface TransactionFactory {

    //创建Transaction
    Transaction newTransaction(DataSource ds, int level, boolean autoCommit);
}