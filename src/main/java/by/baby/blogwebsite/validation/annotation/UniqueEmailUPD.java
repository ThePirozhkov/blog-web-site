package by.baby.blogwebsite.validation.annotation;

import by.baby.blogwebsite.validation.validator.UniqueEmailUPDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueEmailUPDValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmailUPD {
    String message() default "Пользователь с таким e-mail существует!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
