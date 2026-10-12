package io.com.spring_mvc.common.validator;

import com.baomidou.mybatisplus.annotation.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValue, Object> {

    private Class<? extends Enum<?>> enumClass;
    @Override
    public void initialize(EnumValue enumValue) {
        ConstraintValidator.super.initialize(enumValue);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return true;
        }

        // TODO 判断类型是否是枚举类型

        for (Enum e : enumClass.getEnumConstants()) {
            // TODO 实现遍历判断参数是否符合枚举字段
        }

        return false;
    }
}
