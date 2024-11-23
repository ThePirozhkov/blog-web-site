package by.baby.blogwebsite.dto;

import by.baby.blogwebsite.enums.Role;
import lombok.Value;

@Value
public class UserDto {
    Long id;
    String username;
    String password;
    String email;
    Role role;
}
