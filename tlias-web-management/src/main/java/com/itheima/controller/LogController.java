package com.itheima.controller;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
public class LogController {
    @Autowired
    private LogService logService;
    /*
    * 日志分页查询
    * */
    @GetMapping("/log/page")
    public Result pageList(@RequestParam Integer page, @RequestParam Integer pageSize){
        PageResult pageResult = logService.pageList(page, pageSize);
        return Result.success(pageResult);
    }
}
