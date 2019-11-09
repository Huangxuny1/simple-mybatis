package datasource;

import javax.sql.DataSource;
import java.util.Properties;
public interface DataSourceFactory {

  //设置属性,被XMLConfigBuilder所调用
  void setProperties(Properties props);

  //生产数据源,直接得到javax.sql.DataSource
  DataSource getDataSource();

}