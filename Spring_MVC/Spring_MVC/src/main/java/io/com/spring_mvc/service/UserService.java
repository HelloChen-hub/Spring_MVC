package io.com.spring_mvc.service;

import com.baomidou.mybatisplus.spring.service.IService;
import io.com.spring_mvc.entity.User;
import io.com.spring_mvc.query.UserLoginQuery;
import io.com.spring_mvc.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    UserVO login(UserLoginQuery userLoginQuery);

    UserVO queryById(Long id);
}
