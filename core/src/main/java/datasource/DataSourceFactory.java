package datasource;

import javax.sql.DataSource;
import java.util.Properties;

public interface DataSourceFactory {

    void setProperties(Properties props);

    //生产数据源,直接得到javax.sql.DataSource
    DataSource getDataSource();

}