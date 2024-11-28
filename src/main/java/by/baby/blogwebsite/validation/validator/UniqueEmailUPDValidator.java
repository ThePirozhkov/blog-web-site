package by.baby.blogwebsite.validation.validator;

import by.baby.blogwebsite.dto.UpdateUserDto;
import by.baby.blogwebsite.repository.UserRepository;
import by.baby.blogwebsite.validation.annotation.UniqueEmailUPD;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailUPDValidator implements ConstraintValidator<UniqueEmailUPD, UpdateUserDto> {

    private final UserRepository userRepository;

    public UniqueEmailUPDValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(UpdateUserDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findById(dto.getId())
                .map(userEntity ->
                        userEntity.getEmail().equals(dto.getEmail()) || !userRepository.existsByEmail(dto.getEmail()))
                .orElseThrow(() -> new RuntimeException("Validation failed"));
    }
}
