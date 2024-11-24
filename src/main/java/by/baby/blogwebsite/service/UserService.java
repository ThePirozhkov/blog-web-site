package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.RegistrationDto;
import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.mapper.RegistrationDtoMapper;
import by.baby.blogwebsite.mapper.UserDtoMapper;
import by.baby.blogwebsite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final RegistrationDtoMapper registrationDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                 .map(userDtoMapper::mapToUserDto);
    }

    public UserDto save(RegistrationDto registrationDto) {
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        return Optional.of(registrationDto)
                .map(registrationDtoMapper::mapToUserEntity)
                .map(userRepository::saveAndFlush)
                .map(userDtoMapper::mapToUserDto)
                .orElseThrow(() -> new RuntimeException("Cannot save user"));
    }

    public UserDto update(UserDto newUserDto, String username) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    user.setUsername(newUserDto.getUsername());
                    user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
                    user.setEmail(newUserDto.getEmail());
                    user.setRole(newUserDto.getRole());
                    return user;
                })
                .map(userDtoMapper::mapToUserDto)
                .orElseThrow(() -> new RuntimeException("Cannot update user"));
    }

}
