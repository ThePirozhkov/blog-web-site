package by.baby.blogwebsite.dto;

import by.baby.blogwebsite.validation.annotation.CheckRestoreData;
import by.baby.blogwebsite.validation.annotation.ConfirmPasswordRestoreAccess;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@CheckRestoreData
@ConfirmPasswordRestoreAccess
public class RestoreAccessDto {
    @Email(message = "Должен иметь формат E-mail!")
    private String email;
    private String accessKey;
    @Size(min = 8, max = 40, message = "Длина пароля должна быть в диапазоне от 8 до 40!")
    private String password;
    private String confirmPassword;
}
