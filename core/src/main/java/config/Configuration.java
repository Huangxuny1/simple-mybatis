package config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Configuration {
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    private Map<String , MapperStatement> mapperStatementMap = new HashMap<>();

}
