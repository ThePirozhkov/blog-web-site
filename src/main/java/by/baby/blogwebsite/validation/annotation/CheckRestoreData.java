package by.baby.blogwebsite.validation.annotation;

import by.baby.blogwebsite.validation.validator.CheckRestoreDataValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Constraint(validatedBy = CheckRestoreDataValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRestoreData {
    String message() default "Ключ или email введены неправильно!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
