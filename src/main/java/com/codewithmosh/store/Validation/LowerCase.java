package com.codewithmosh.store.Validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LowerClassValidator.class)
public @interface LowerCase {
    String message() default "Must be lowercase";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}
