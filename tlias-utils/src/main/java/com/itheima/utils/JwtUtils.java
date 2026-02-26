package com.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "aXRoZWltYQ=="; // 秘钥
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000; // 12小时

    /**
     * 生成JWT令牌
     * @param claims 令牌中包含的信息
     * @return 生成的JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder() // 创建JWT构建器
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)// 设置签名算法和密钥
                .addClaims(claims)// 添加令牌中包含的信息
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))// 设置令牌过期时间
                .compact();// 生成令牌
    }

    /**
     * 解析JWT令牌
     * @param token 要解析的JWT令牌字符串
     * @return 包含令牌信息的Claims对象
     * @throws Exception 如果令牌无效或已过期，则抛出异常
     */
    public static Claims parseToken(String token) throws Exception {
        return Jwts.parser()// 创建JWT解析器
                .setSigningKey(SECRET_KEY)// 设置密钥
                .parseClaimsJws(token)// 解析令牌
                .getBody();// 获取令牌信息
    }
}