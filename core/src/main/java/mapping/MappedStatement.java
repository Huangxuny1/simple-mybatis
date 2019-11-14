package mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


//用于存储xml文件里的数据结构，namespace,id,resultmap,sql语句。
//一个MapperStatement对象只能保存一个sql语句信息
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappedStatement {
    private String namespace;
    private String sqlId;
    private String resultType;
    private String sql;
    private String originSql;
    private SqlType sqlType;


}
