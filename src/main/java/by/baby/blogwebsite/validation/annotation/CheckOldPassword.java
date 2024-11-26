package by.baby.blogwebsite.validation.annotation;

import by.baby.blogwebsite.validation.validator.CheckOldPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Constraint(validatedBy = CheckOldPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckOldPassword {
    String message() default "Неправильно введен старый пароль!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
