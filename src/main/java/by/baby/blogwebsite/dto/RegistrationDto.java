package by.baby.blogwebsite.dto;

import by.baby.blogwebsite.validation.annotation.ConfirmPassword;
import by.baby.blogwebsite.validation.annotation.UniqueEmail;
import by.baby.blogwebsite.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@ConfirmPassword
public final class RegistrationDto {
    @NotBlank(message = "Имя не должно быть пустым!")
    @UniqueUsername
    @Size(min = 3, max = 20, message = "Длина имени должна быть в диапазоне от 3 до 20!")
    private String username;
    @Size(min = 8, max = 40, message = "Длина пароля должна быть в диапазоне от 8 до 40!")
    private String password;
    private String confirmPassword;
    @NotBlank(message = "E-mail не должен быть пустым!")
    @Email(message = "Должен иметь формат E-mail!")
    @UniqueEmail
    private String email;
}
