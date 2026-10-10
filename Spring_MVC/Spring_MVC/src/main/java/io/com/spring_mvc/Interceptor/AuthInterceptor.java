package io.com.spring_mvc.Interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.com.spring_mvc.common.RedisPrefix;
import io.com.spring_mvc.common.UserContext;
import io.com.spring_mvc.util.JwtUtil;
import io.com.spring_mvc.util.Result;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 从请求头获取token
        String token = request.getHeader("Authorization");

        // 判断token是否存在
        if (token == null || token.isBlank()) {
            log.info("token无效");
            return responseWriteError(response, 401, "未登录，请先登录");
        }

        // 判断token是否合法
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Claims claims;
        try {
            claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            return responseWriteError(response, 401, "token无效或者已经过期");
        }

        // 查询Redis，判断用户信息是否过期
        Long userId = claims.get("userId", Long.class);

        if (!stringRedisTemplate.hasKey(RedisPrefix.USER_TOKEN)) {
            log.info("登录过期");
            return responseWriteError(response, 401, "登录过期，请重新登录");
        }

        // 更新用户登录截止时间
        stringRedisTemplate.opsForValue().set(RedisPrefix.USER_TOKEN, token, RedisPrefix.USER_TOKEN_EXPIRE, TimeUnit.MILLISECONDS);

        // 将用户信息存入ThreadLocal
        String role = claims.get("role", String.class);
        UserContext.setUserId(userId);
        UserContext.setRoleHolder(role);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 请求处理完成后清理ThreadLocal，防止线程复用导致用户信息串号
        UserContext.clear();
    }

    private boolean responseWriteError(HttpServletResponse response, int code, String message) {

        // 以统一的 Result 格式编写 JSON 返回信息
        try {
            response.setStatus(code);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(Result.error(code, message)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
