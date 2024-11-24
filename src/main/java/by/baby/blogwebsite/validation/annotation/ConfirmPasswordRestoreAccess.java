package by.baby.blogwebsite.validation.annotation;

import by.baby.blogwebsite.validation.validator.ConfirmPasswordRestoreAccessValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Constraint(validatedBy = ConfirmPasswordRestoreAccessValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPasswordRestoreAccess {
    String message() default "Пароль не совпадает!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
