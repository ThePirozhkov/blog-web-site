package by.baby.blogwebsite.validation.validator;

import by.baby.blogwebsite.dto.RegistrationDto;
import by.baby.blogwebsite.validation.annotation.ConfirmPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, RegistrationDto> {

    @Override
    public boolean isValid(RegistrationDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return (dto.getConfirmPassword().equals(dto.getPassword()));
    }
}
