package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.RestoreAccessDto;
import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.mapper.UserDtoMapper;
import by.baby.blogwebsite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestoreAccessService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto restoreAccess(RestoreAccessDto restoreAccessDto) {
        return userRepository.findByEmail(restoreAccessDto.getEmail())
                .map(userEntity -> {
                    userEntity.setPassword(passwordEncoder.encode(restoreAccessDto.getPassword()));
                    return userEntity;
                })
                .map(userRepository::saveAndFlush)
                .map(userDtoMapper::mapToUserDto)
                .orElseThrow(() -> new RuntimeException("User not restored!"));
    }

}
