package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    Logger LOGGER = LoggerFactory.getLogger(UserDetailService.class);

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user: " + username));
        UserDetails user =  User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .authorities(Collections.singleton(userDto.getRole()))
                .build();
        LOGGER.info("UserDetails: {}", user);
        return user;
    }
}
