package by.baby.blogwebsite.dto;

import by.baby.blogwebsite.validation.annotation.UniqueEmail;
import by.baby.blogwebsite.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public final class RegistrationDto {
    @NotBlank
    @UniqueUsername
    private String username;
    @Size(min = 8, max = 40)
    private String password;
    @Email
    @UniqueEmail
    private String email;
}
