package by.baby.blogwebsite.validation.annotation;

import by.baby.blogwebsite.validation.validator.CheckNewPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Constraint(validatedBy = CheckNewPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNewPassword {
    String message() default "Новый пароль совпадает со старым!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
