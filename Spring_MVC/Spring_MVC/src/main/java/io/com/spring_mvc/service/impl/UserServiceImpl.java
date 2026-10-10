package io.com.spring_mvc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.repository.AbstractRepository;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import io.com.spring_mvc.common.RedisPrefix;
import io.com.spring_mvc.entity.User;
import io.com.spring_mvc.exception.BusinessException;
import io.com.spring_mvc.mapper.UserMapper;
import io.com.spring_mvc.query.UserLoginQuery;
import io.com.spring_mvc.service.UserService;
import io.com.spring_mvc.util.JwtUtil;
import io.com.spring_mvc.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public UserVO login(UserLoginQuery userLoginQuery) {

        // 判断参数是否为空
        if (userLoginQuery == null) {
            log.info("信息为空");
            throw new BusinessException(401, "用户信息为空");
        }

        // 查询用户是否存在
        User user = lambdaQuery()
                .eq(User::getUsername, userLoginQuery.getUsername())
                .one();

        if (user == null) {
            log.info("用户名错误");
            throw new BusinessException(402, "用户名错误");
        }

        // 比较密码是否匹配
        if (!user.getPassword().equals(userLoginQuery.getPassword())) {
            log.info("密码错误");
            throw new BusinessException(402, "密码错误");
        }

        // 判断用户状态
        if (user.getStatus() == 0) {
            log.info("用户已被禁用");
            throw new BusinessException(403, "用户已被禁用");
        }

        // 为用户生成token
        String token = JwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        UserVO userVO = new UserVO(user.getId(), user.getUsername(), token);

        // 将用户信息存储到redis
        stringRedisTemplate.opsForValue().set(RedisPrefix.USER_TOKEN, token, RedisPrefix.USER_TOKEN_EXPIRE, TimeUnit.MILLISECONDS);

        return userVO;
    }

    @Override
    public UserVO queryById(Long id) {
        // 判读id是否合法
        if (id == null) {
            log.info("id不合法");
            throw new BusinessException("id为空");
        }

        User user = getById(id);

        // 判断用户是否存在
        if (user == null) {
            log.info("用户不存在");
            throw new BusinessException(404, "用户不存在");
        }

        return BeanUtil.copyProperties(user, UserVO.class);
    }
}
