package by.baby.blogwebsite.dto;

import by.baby.blogwebsite.validation.annotation.UniqueEmailUPD;
import by.baby.blogwebsite.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@UniqueEmailUPD
public final class UpdateUserDto {
    private Long id;
    @UniqueUsername
    @Size(min = 3, max = 20, message = "Длина имени должна быть в диапазоне от 3 до 20!")
    private String username;
    private String email;

}
