package io.com.spring_mvc.common.validator;

import io.com.spring_mvc.common.annotation.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValue, Object> {

    private Class<? extends Enum<?>> enumClass;
    @Override
    public void initialize(EnumValue enumValue) {

        this.enumClass = enumValue.enumClass();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return true;
        }

        // TODO 判断类型是否是枚举类型
        Enum<?>[] enums = enumClass.getEnumConstants();
        if (enums == null) {
            return false;
        }
        for (Enum e : enumClass.getEnumConstants()) {
            // TODO 实现遍历判断参数是否符合枚举字段
            if (e.name().equals(object.toString())) {
                return true;
            }
        }

        // 枚举值没匹配到，校验失败
        // 取消默认错误信息
        constraintValidatorContext.disableDefaultConstraintViolation();
        // 自定义错误信息
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                "值必须是以下之一：" + Arrays.toString(enums)
        ).addConstraintViolation();

        return false;
    }
}
