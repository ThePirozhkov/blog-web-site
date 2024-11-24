package by.baby.blogwebsite.dto;

import by.baby.blogwebsite.validation.annotation.CheckRestoreData;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@CheckRestoreData
public class RestoreAccessDto {
    @Email
    private String email;
    private String accessKey;
    @Size(min = 8, max = 40)
    private String password;
    private String confirmPassword;
}
