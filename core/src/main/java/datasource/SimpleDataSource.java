package datasource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

/**
 * 一个简单的 DataSource
 * UnPooled
 */
@NoArgsConstructor
@Getter
@Setter
public class SimpleDataSource implements DataSource {
    private ClassLoader driverClassLoader;
    private Properties driverProperties;

    private static ConcurrentMap<String, Driver> registeredDrivers = new ConcurrentHashMap<>();

    private String driver;
    private String url;
    private String username;
    private String password;

    // 11-09 添加事务相关
    private Boolean autoCommit;
    private Integer transactionIsolationLevel;

    static {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            registeredDrivers.put(driver.getClass().getName(), driver);
        }
    }


    public SimpleDataSource(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public SimpleDataSource(String driver, String url, String username, String password, Integer transactionIsolationLevel) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.transactionIsolationLevel = transactionIsolationLevel;
    }

    public SimpleDataSource(ClassLoader classLoader, String driver, String url, String username, String password) {
        this.driverClassLoader = classLoader;
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public SimpleDataSource(ClassLoader classLoader, String driver, String url, String username, String password, Integer transactionIsolationLevel) {
        this.driverClassLoader = classLoader;
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.transactionIsolationLevel = transactionIsolationLevel;
    }


    private Connection doGetConnection() throws SQLException {
        Properties props = new Properties();
        if (username != null) {
            props.setProperty("user", username);
        }
        if (password != null) {
            props.setProperty("password", password);
        }
        initDriver();
        Connection connection = DriverManager.getConnection(url, props);
        configureConnection(connection);
        return connection;
    }

    private void configureConnection(Connection conn) throws SQLException {
        if (autoCommit != null && autoCommit != conn.getAutoCommit()) {
            conn.setAutoCommit(autoCommit);
        }
        if (null != transactionIsolationLevel) {
            conn.setTransactionIsolation(transactionIsolationLevel);
        }
    }

    private synchronized void initDriver() {
        if (!registeredDrivers.containsKey(driver)) {
            Class<?> driverType;
            try {
                if (null != driverClassLoader) {
                    driverType = Class.forName(driver, true, driverClassLoader);
                } else {
                    driverType = Class.forName(driver);
                }
                Driver driverInstance = (Driver) driverType.newInstance();
                DriverManager.registerDriver(driverInstance);
                registeredDrivers.put(driver, driverInstance);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
