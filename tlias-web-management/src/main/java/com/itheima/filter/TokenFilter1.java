package com.itheima.filter;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
//@WebFilter( "/*")
public class TokenFilter1 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TokenFilter1 初始化...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("TokenFilter1 拦截请求: {}", request.getRequestURI());
        //1. 获取到请求路径,并校验是否是登录请求
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")){
            log.info("登录请求, 放行");
            filterChain.doFilter(servletRequest, servletResponse);
            return; //表示放行后不在继续执行后面的过滤器或者资源
        }
        //2. 获取请求头中的令牌
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()){
            log.info("令牌为空, 响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //3. 校验令牌
        try {
            JwtUtils.parseToken(token);
        }catch (Exception e){
            log.info("令牌非法, 响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //4. 走到这里说明令牌合法, 放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("TokenFilter1 销毁...");
    }
}
