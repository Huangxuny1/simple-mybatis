package session.impl;

import datasource.SimpleDataSource;
import executor.BetterSimpleExecutor;
import executor.Executor;
import session.Configuration;
import session.SqlSession;
import session.SqlSessionFactory;
import transaction.Transaction;
import transaction.TransactionFactory;
import transaction.impl.JDBCTransaction;
import transaction.impl.JDBCTransactionFactory;
import utils.Constants;
import utils.XmlUtil;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.sql.Connection;

public class SimpleSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public SimpleSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        loadMapper(Configuration.PROPS.getProperty("mapper"));
    }

    @Override
    public SqlSession openSession() {
        return openSession(false);
    }


    @Override
    public SqlSession openSession(boolean autoCommit) {
        return openSessionFromDataSource(autoCommit);
    }


    private SqlSession openSessionFromDataSource(boolean autoCommit) {
        // 这里源码是从 environment 中取 factory
        final TransactionFactory transactionFactory = new JDBCTransactionFactory();
        DataSource dataSource = new SimpleDataSource(
                Configuration.PROPS.getProperty("db.driver"),
                Configuration.PROPS.getProperty("db.url"),
                Configuration.PROPS.getProperty("db.username"),
                Configuration.PROPS.getProperty("db.password")
        );

        Transaction transaction = transactionFactory.newTransaction(dataSource, 0, false);
        // todo 源码中executor是configuration中实例化的 并且支持 interceptor -- 未来如果有时间会支持插件
        Executor executor = new BetterSimpleExecutor(configuration, transaction);
        return new SimpleSqlSession(configuration, executor);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    private void loadMapper(String path) {
        URL resources = SimpleSqlSessionFactory.class.getClassLoader().getResource(path);
        File file = new File(resources.getFile());
        if (file.isDirectory()) {
            File[] mappers = file.listFiles();
            for (File mapper : mappers) {
                if (file.getName().endsWith(Constants.MAPPER_FILE_SUFFIX)) {
                    XmlUtil.readMapperXml(mapper, configuration);
                }
            }
        } else if (file.getName().endsWith(Constants.MAPPER_FILE_SUFFIX)) {
            XmlUtil.readMapperXml(file, configuration);
        }
    }
}
