package by.baby.blogwebsite.mapper;

import by.baby.blogwebsite.dto.RegistrationDto;
import by.baby.blogwebsite.enums.Role;
import by.baby.blogwebsite.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class RegistrationDtoMapper {

    public UserEntity mapToUserEntity(RegistrationDto registrationDto) {
        return new UserEntity(
                Role.USER,
                registrationDto.getEmail(),
                registrationDto.getPassword(),
                registrationDto.getUsername(),
                null);
    }

}
