package by.baby.blogwebsite.validation.validator;

import by.baby.blogwebsite.dto.RestoreAccessDto;
import by.baby.blogwebsite.validation.annotation.ConfirmPasswordRestoreAccess;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordRestoreAccessValidator implements
        ConstraintValidator<ConfirmPasswordRestoreAccess, RestoreAccessDto> {

    @Override
    public boolean isValid(RestoreAccessDto restoreAccessDto, ConstraintValidatorContext constraintValidatorContext) {
        return restoreAccessDto.getPassword().equals(restoreAccessDto.getConfirmPassword());
    }
}
