package by.baby.blogwebsite.validation.validator;

import by.baby.blogwebsite.dto.UpdatePassDto;
import by.baby.blogwebsite.validation.annotation.CheckNewPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckNewPasswordValidator implements ConstraintValidator<CheckNewPassword, UpdatePassDto> {

    @Override
    public boolean isValid(UpdatePassDto updatePassDto, ConstraintValidatorContext constraintValidatorContext) {
        return !updatePassDto.getOldPassword().equals(updatePassDto.getNewPassword());
    }
}
