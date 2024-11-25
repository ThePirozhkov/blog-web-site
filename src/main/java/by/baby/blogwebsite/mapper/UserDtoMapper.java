package by.baby.blogwebsite.mapper;

import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.persistence.entity.UserEntity;
import by.baby.blogwebsite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDtoMapper {

    private final UserRepository userRepository;

    public UserDto mapToUserDto(UserEntity user) {
        return Optional.of(new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRole(),
                        user.getRestoreKey()))
                .orElseThrow(() -> new RuntimeException("User not mapped"));
    }

    public UserEntity mapToUserEntity(UserDto userDto) {
        return userRepository.findById(userDto.getId())
                .orElse(null);
    }
}
