package by.baby.blogwebsite.validation.validator;

import by.baby.blogwebsite.dto.UpdateUserDto;
import by.baby.blogwebsite.repository.UserRepository;
import by.baby.blogwebsite.validation.annotation.UniqueUsernameUPD;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UniqueUsernameUPDValidator implements ConstraintValidator<UniqueUsernameUPD, UpdateUserDto> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UpdateUserDto user, ConstraintValidatorContext constraintValidatorContext) {
        return (userRepository.existsByUsernameAndId(user.getUsername(), user.getId()) || !userRepository.existsByUsername(user.getUsername()));
    }

}
