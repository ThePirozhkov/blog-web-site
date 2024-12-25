package by.baby.blogwebsite.validation.annotation;

import by.baby.blogwebsite.validation.validator.UniqueUsernameUPDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueUsernameUPDValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsernameUPD {
    String message() default "Пользователь с таким именем существует!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
