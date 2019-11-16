package com.huangxunyi.mybatis;

import java.util.List;

public interface LogMapper {

    Log getLogById(String id);

    List<Log> getAllLog();

    void updateLog(Log log);

    void insertLog(Log log);
}
