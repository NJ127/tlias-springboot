package com.itheima.mapper;

import com.itheima.pojo.EmpLoginLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    @Insert("insert into emp_login_log values(#{id},#{username},#{password},#{loginTime},#{isSuccess},#{jwt},#{costTime})")
    void insertLog(EmpLoginLog log);
}
