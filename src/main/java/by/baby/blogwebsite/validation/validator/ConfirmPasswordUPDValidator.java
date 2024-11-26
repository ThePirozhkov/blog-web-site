package by.baby.blogwebsite.validation.validator;

import by.baby.blogwebsite.dto.UpdatePassDto;
import by.baby.blogwebsite.validation.annotation.ConfirmPasswordUPD;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordUPDValidator implements ConstraintValidator<ConfirmPasswordUPD, UpdatePassDto> {

    @Override
    public boolean isValid(UpdatePassDto updatePassDto, ConstraintValidatorContext constraintValidatorContext) {
        return updatePassDto.getNewPassword().equals(updatePassDto.getConfirmPassword());
    }
}
