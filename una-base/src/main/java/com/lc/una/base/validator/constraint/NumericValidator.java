package com.lc.una.base.validator.constraint;

import com.lc.una.utils.tools.StringUtils;
import com.lc.una.base.validator.annotion.Numeric;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断是否为数字【校验器】
 *
 * @author LC
 * @date 2019年12月4日13:16:36
 */
public class NumericValidator implements ConstraintValidator<Numeric, String> {
    @Override
    public void initialize(Numeric constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || StringUtils.isBlank(value)) {
            return false;
        }
        if (!StringUtils.isNumeric(value)) {
            return false;
        }
        return true;
    }
}
