package datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 一个简单的 DataSource
 * UnPooled
 */
public class SimpleDataSource implements DataSource {


    private String driver;
    private String url;
    private String username;
    private String password;

    public SimpleDataSource() {
    }

    public SimpleDataSource(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection doGetConnection() throws SQLException {
        initDriver();
        Properties props = new Properties();
        if (username != null) {
            props.setProperty("user", username);
        }
        if (password != null) {
            props.setProperty("password", password);
        }

        Connection connection = DriverManager.getConnection(url, props);
        //configureConnection
        return connection;
    }

    private synchronized void initDriver() {
        try {
            Class<?> driverClazz = Class.forName(driver);
//            Driver driver= (Driver) driverClazz.newInstance();
//            DriverManager.registerDriver(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        return doGetConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return doGetConnection();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        // return null;
        throw new SQLException(getClass().getName() + " is not a wrapper.");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
}
