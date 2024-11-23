package by.baby.blogwebsite.validation.annotation;

import by.baby.blogwebsite.validation.validator.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Пользователь с таким именем существует!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
