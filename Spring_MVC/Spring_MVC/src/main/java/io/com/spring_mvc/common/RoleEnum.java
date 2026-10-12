package io.com.spring_mvc.common;

public enum RoleEnum {

    USER("普通用户"),
    DOCTOR("中医"),
    MERCHANT("商家"),
    ADMIN("管理员");

    private final String desc;

    RoleEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
