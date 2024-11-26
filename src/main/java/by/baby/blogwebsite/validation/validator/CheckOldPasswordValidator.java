package by.baby.blogwebsite.validation.validator;

import by.baby.blogwebsite.dto.UpdatePassDto;
import by.baby.blogwebsite.repository.UserRepository;
import by.baby.blogwebsite.validation.annotation.CheckOldPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CheckOldPasswordValidator implements ConstraintValidator<CheckOldPassword, UpdatePassDto> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CheckOldPasswordValidator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isValid(UpdatePassDto updatePassDto, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findById(updatePassDto.getId())
                .map(user -> passwordEncoder.matches(updatePassDto.getOldPassword(), user.getPassword()))
                .orElseThrow(() -> new RuntimeException("Validation failed"));
    }
}
