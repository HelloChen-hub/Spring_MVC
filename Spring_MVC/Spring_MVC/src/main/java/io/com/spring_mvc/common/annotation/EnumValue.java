package io.com.spring_mvc.common.annotation;

import io.com.spring_mvc.common.RoleEnum;
import io.com.spring_mvc.common.validator.EnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValue {

    // 错误信息
    String message() default "枚举值不合法";

    // 分组类型
    Class<?>[] groups() default {};

    // 携带元数据，用于区分错误的严重程度
    Class<? extends Payload>[] payload() default {};

    // 枚举类型
    Class<? extends Enum<?>> enumClass();
}
