package com.itheima.service;

import com.itheima.pojo.PageResult;

public interface LogService {
    /*
    * 日志分页查询
    * */
    PageResult pageList(int page, int size);
}
