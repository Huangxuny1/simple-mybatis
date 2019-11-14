package com.huangxunyi.mapper;

import com.huangxunyi.Log;

import java.util.List;

public interface TestMapper {
    /*
    <select id="getLog" resultType="com.huangxunyi.datasource.Log">
        select * from log where id = #{id}
    </select>

    <select id="getAll" resultType="com.huangxunyi.datasource.Log">
        select * from log
    </select>

    <update id="updateLog">
        update log set message = '#{message}' where id = #{id}
    </update>

    <insert id="insertLog">
        insert into log values (#{id},#{},#{},#{},#{},#{})
    </insert>
     */
    public Log getLog(String id);

    public List<Log> getAll();

    public int updateLog(Log log);

    public int insertLog(Log log);

}
