package com.itheima.mapper;

import com.itheima.pojo.OperateLog;
import com.itheima.pojo.PageResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    void insert(OperateLog log);

    //分页查询日志数据
    @Select("select id, operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time from operate_log")
    List<OperateLog> pageList();
}
