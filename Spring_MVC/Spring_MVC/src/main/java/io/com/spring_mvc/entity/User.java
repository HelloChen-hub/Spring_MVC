package io.com.spring_mvc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("Sys_user")
public class User {

    private Long id;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String role;      // USER / DOCTOR / MERCHANT
    private Integer status;   // 1-启用，0-禁用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
