package com.huangxunyi.mapper;

import com.huangxunyi.Log;

import java.util.List;

public interface TestMapper {

    Log getLog(String id);

    List<Log> getAll();

    int updateLog(Log log);

    int insertLog(Log log);

    int deleteLog(Log log);

    int deleteLog(String id);

}
