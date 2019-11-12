package transaction.impl;

import transaction.Transaction;
import transaction.TransactionFactory;

import javax.sql.DataSource;

public class JDBCTransactionFactory implements TransactionFactory {
    @Override
    public Transaction newTransaction(DataSource ds, int level, boolean autoCommit) {
        return new JDBCTransaction(ds, level, autoCommit);
    }
}
