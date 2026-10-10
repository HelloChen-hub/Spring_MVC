package io.com.spring_mvc.common;

public class UserContext {

    // 当前登录的用户id
    private static final ThreadLocal<Long> USER_ID_HOLDER = new ThreadLocal<>();

    // 当前登录的用户角色
    private static final ThreadLocal<String> ROLE_HOLDER = new ThreadLocal<>();

    // 暴露给外部调用的赋值方法
    public static void setUserId(Long id) {
        USER_ID_HOLDER.set(id);
    }

    public static void setRoleHolder(String role) {
        ROLE_HOLDER.set(role);
    }

    // 清理用户信息
    public static void clear() {
        USER_ID_HOLDER.remove();
        ROLE_HOLDER.remove();
    }

}
