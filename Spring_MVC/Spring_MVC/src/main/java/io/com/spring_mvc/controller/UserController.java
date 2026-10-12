package io.com.spring_mvc.controller;

import io.com.spring_mvc.common.annotation.CurrentUser;
import io.com.spring_mvc.query.UserLoginQuery;
import io.com.spring_mvc.service.UserService;
import io.com.spring_mvc.util.Result;
import io.com.spring_mvc.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserLoginQuery userLoginQuery) {
        UserVO result = userService.login(userLoginQuery);
        return Result.success(result);
    }

    @GetMapping("/id")
    public Result<UserVO> query(@CurrentUser Long id) {
        UserVO userVO = userService.queryById(id);
        return Result.success(userVO);
    }
}
