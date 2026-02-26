package com.itheima.aop;

import com.itheima.mapper.LoginMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpLoginLog;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class EmploginAspect {
    @Autowired
    private LoginMapper loginMapper;

    @Around("execution(* com.itheima.controller.LoginController.login(..))")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        // 构建登录日志实体
        EmpLoginLog elog = new EmpLoginLog();
        Emp emp = (Emp) joinPoint.getArgs()[0];
        Result resultObject = (Result) result;

        // 判空处理：确保 resultObject 不为空
        if (resultObject == null) {
            throw new RuntimeException("登录结果为空，无法记录日志");
        }

        elog.setUsername(emp.getUsername()); // 用户名
        elog.setPassword(emp.getPassword()); // 密码
        elog.setLoginTime(LocalDateTime.now()); // 登录时间
        elog.setIsSuccess(resultObject.getCode().shortValue()); // 是否登录成功

        // 判空处理：确保 data 不为空且为 LoginInfo 类型
        Object data = resultObject.getData();
        if (data instanceof LoginInfo) {
            LoginInfo loginInfo = (LoginInfo) data;
            elog.setJwt(loginInfo.getToken()); // JWT令牌
        } else {
            // 如果 data 不是 LoginInfo，记录警告日志并跳过 JWT 字段
            log.warn("登录结果中的 data 不是 LoginInfo 类型，JWT 字段将留空");
            elog.setJwt(null); // JWT 令牌设为空
        }

        elog.setCostTime(costTime);

        loginMapper.insertLog(elog);
        return result;
    }
}
