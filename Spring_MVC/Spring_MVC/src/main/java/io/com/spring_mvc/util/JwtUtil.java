package io.com.spring_mvc.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // 签名密钥（HS256 要求密钥长度不少于 256 位，即 32 字节）
    private static final String SECRET_KEY = "io.chn.secret.key.io.chn.secret.key";

    // token 有效期
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    // 生成签名用的密钥对象
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // 生成token
    public static String generateToken(Long id, String username, String role) {

        // 存储用户信息键值对
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", id);
        claims.put("username", username);
        claims.put("role", role);

        // 生成token字符串
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();

    }

    // 解析token
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
