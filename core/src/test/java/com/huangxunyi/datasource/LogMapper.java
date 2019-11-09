package com.huangxunyi.datasource;

import java.util.List;

public interface LogMapper {

    /**
     * 获取单个user
     * 
     * @param id
     * @return 
     * @see 
     */
    Log getLog(String id);
    
    /**
     * 获取所有用户
     * 
     * @return 
     * @see 
     */
    List<Log> getAll();
    
    /**
     * 更新用户（功能未完成）
     * 
     * @param id 
     */
    void updateLog(String id);
}