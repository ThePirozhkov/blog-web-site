package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.RegistrationDto;
import by.baby.blogwebsite.dto.UpdatePassDto;
import by.baby.blogwebsite.dto.UpdateUserDto;
import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.mapper.RegistrationDtoMapper;
import by.baby.blogwebsite.mapper.UserDtoMapper;
import by.baby.blogwebsite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userDtoMapper::mapToUserDto);
    }

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

    public UserDto updateUser(UpdateUserDto dto, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(dto.getUsername());
                    user.setEmail(dto.getEmail());
                    return user;
                })
                .map(userDtoMapper::mapToUserDto)
                .orElseThrow(() -> new RuntimeException("Cannot update user"));
    }

    public UserDto updatePass(UpdatePassDto dto, Long id) {
        logger.info("User: {}", userRepository.findById(id));
        return userRepository.findById(id)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
                    return user;
                })
                .map(userDtoMapper::mapToUserDto)
                .orElseThrow(() -> new RuntimeException("Cannot update pass"));
    }

    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        if (userRepository.existsById(id)) {
            return true;
        } else {
            throw new RuntimeException("Cannot delete user");
        }
    }

}
