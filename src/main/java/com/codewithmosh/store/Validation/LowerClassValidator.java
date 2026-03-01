package com.codewithmosh.store.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LowerClassValidator implements ConstraintValidator<LowerCase,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value==null) return true;   //since null value cant be distinguished in upper or lowercase
        return value.equals(value.toLowerCase());
    }
}
