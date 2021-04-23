package com.lc.una.base.validator.constraint;

import  com.lc.una.utils.tools.StringUtils;
import com.lc.una.base.global.Constants;
import com.lc.una.base.validator.annotion.IdValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ID校验器，主要判断是否为空，并且长度是否为32
 *
 * @author LC
 * @date 2019年12月4日22:48:43
 */
public class IdValidator implements ConstraintValidator<IdValid, String> {


    @Override
    public void initialize(IdValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || StringUtils.isBlank(value) || StringUtils.isEmpty(value.trim()) || value.length() != Constants.THIRTY_TWO) {
            return false;
        }
        return true;
    }
}
