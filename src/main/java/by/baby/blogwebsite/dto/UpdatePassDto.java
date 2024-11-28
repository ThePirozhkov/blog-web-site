package by.baby.blogwebsite.dto;

import by.baby.blogwebsite.validation.annotation.CheckNewPassword;
import by.baby.blogwebsite.validation.annotation.CheckOldPassword;
import by.baby.blogwebsite.validation.annotation.ConfirmPasswordUPD;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@CheckOldPassword
@CheckNewPassword
@ConfirmPasswordUPD
public class UpdatePassDto {
    private Long id;
    private String oldPassword;
    @Size(min = 8, max = 40, message = "Длина пароля должна быть в диапазоне от 8 до 40!")
    private String newPassword;
    private String confirmPassword;
}
