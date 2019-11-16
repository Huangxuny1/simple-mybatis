package session.impl;

import datasource.DataSourceFactory;
import datasource.SimpleDataSource;
import datasource.SimpleDataSourceFactory;
import executor.SimpleExecutor;
import executor.Executor;
import session.Configuration;
import session.SqlSession;
import session.SqlSessionFactory;
import transaction.Transaction;
import transaction.TransactionFactory;
import transaction.impl.JDBCTransactionFactory;
import utils.Constants;
import utils.XmlUtil;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;

public class SimpleSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public SimpleSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        loadMapper(Configuration.PROPS.getProperty("mapper"));
    }

    @Override
    public SqlSession openSession() {
        return openSession(true);
    }


    @Override
    public SqlSession openSession(boolean autoCommit) {
        return openSessionFromDataSource(autoCommit);
    }


    private SqlSession openSessionFromDataSource(boolean autoCommit) {
        // 这里源码是从 environment 中取 factory
        final TransactionFactory transactionFactory = new JDBCTransactionFactory();
        DataSourceFactory factory = new SimpleDataSourceFactory();
        factory.setProperties(Configuration.PROPS);
        DataSource dataSource = factory.getDataSource();
        Transaction transaction = transactionFactory.newTransaction(dataSource, 0, autoCommit);
        // todo 源码中executor是configuration中实例化的 并且支持 interceptor -- 未来如果有时间会支持插件
        Executor executor = new SimpleExecutor(configuration, transaction);
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
                if (mapper.getName().endsWith(Constants.MAPPER_FILE_SUFFIX)) {
                    XmlUtil.readMapperXml(mapper, configuration);
                }
            }
        } else if (file.getName().endsWith(Constants.MAPPER_FILE_SUFFIX)) {
            XmlUtil.readMapperXml(file, configuration);
        }
    }
}
