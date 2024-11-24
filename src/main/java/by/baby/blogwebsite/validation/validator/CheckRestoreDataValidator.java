package by.baby.blogwebsite.validation.validator;

import by.baby.blogwebsite.dto.RestoreAccessDto;
import by.baby.blogwebsite.repository.UserRepository;
import by.baby.blogwebsite.validation.annotation.CheckRestoreData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CheckRestoreDataValidator implements ConstraintValidator<CheckRestoreData, RestoreAccessDto> {

    private final UserRepository userRepository;

    public CheckRestoreDataValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValid(RestoreAccessDto restoreAccessDto, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByEmail(restoreAccessDto.getEmail())
                .map(userEntity -> userEntity.getEmail().equals(restoreAccessDto.getEmail())
                        && userEntity.getRestoreKey().equals(restoreAccessDto.getAccessKey()))
                .orElse(false);
    }
}
