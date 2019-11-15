package datasource;

import javax.sql.DataSource;
import java.util.Properties;

public class SimpleDataSourceFactory implements DataSourceFactory {
    private DataSource dataSource;

    private static final String DRIVER_PROPERTY_PREFIX = "driver.";
    private static final int DRIVER_PROPERTY_PREFIX_LENGTH = DRIVER_PROPERTY_PREFIX.length();

    public SimpleDataSourceFactory() {
        //this.dataSource = new SimpleDataSource();
    }

    @Override
    public void setProperties(Properties props) {
        this.dataSource = new SimpleDataSource(
                props.getProperty("db.driver"),
                props.getProperty("db.url"),
                props.getProperty("db.username"),
                props.getProperty("db.password")
        );
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
