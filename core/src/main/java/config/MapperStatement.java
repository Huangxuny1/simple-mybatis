package config;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MapperStatement {
    private String namespace;
    private String sourceId;
    private String resultType;
    private String sql;



}
