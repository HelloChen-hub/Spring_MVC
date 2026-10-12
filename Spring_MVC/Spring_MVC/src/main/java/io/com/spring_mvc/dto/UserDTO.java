package io.com.spring_mvc.dto;

import io.com.spring_mvc.common.commInterface.CreateGroup;
import io.com.spring_mvc.common.commInterface.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    @NotBlank(message = "id不能为空", groups = UpdateGroup.class)
    private Long id;

    @NotBlank(message = "用户名不能为空", groups = CreateGroup.class)
    @Size(min = 6, max = 20,  message = "用户名长度必须在3-20之间")
    private String username;

    @NotBlank(message = "密码不能为空", groups = CreateGroup.class)
    @Size(min = 8, max = 16, message = "密码大小设置在8-16个字符区间", groups = CreateGroup.class)

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[^ ]+$", message = "手机号格式不正确",  groups = CreateGroup.class)
    private String password;

    @NotBlank(message = "真实姓名不能为空", groups = CreateGroup.class)
    private String realName;

    @NotBlank(message = "手机号不能为空", groups = CreateGroup.class)
    @Pattern(regexp = "^1[3-9]\\d{9}$", groups = CreateGroup.class)
    private String phone;

    @NotBlank(message = "角色不能为空", groups = CreateGroup.class)
    private String role;      // USER / DOCTOR / MERCHANT

    private Integer status;// 1-启用，0-禁用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
