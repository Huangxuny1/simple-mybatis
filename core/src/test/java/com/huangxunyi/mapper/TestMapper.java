package com.huangxunyi.mapper;

import com.huangxunyi.Log;

import java.util.List;

public interface TestMapper {

    public Log getLog(String id);

    public List<Log> getAll();

    public int updateLog(Log log);

    public int insertLog(Log log);

}
