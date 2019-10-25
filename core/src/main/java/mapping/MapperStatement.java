package mapping;

//用于存储xml文件里的数据结构，namespace,id,resultmap,sql语句。
//一个MapperStatement对象只能保存一个sql语句信息
public class MapperStatement {
    private String namespace;
    private String sourceId;
    private String resultType;
    private String sql;

    public MapperStatement() {
    }

    public MapperStatement(String namespace, String sourceId, String resultType, String sql) {
        this.namespace = namespace;
        this.sourceId = sourceId;
        this.resultType = resultType;
        this.sql = sql;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
