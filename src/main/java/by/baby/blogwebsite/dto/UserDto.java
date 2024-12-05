package by.baby.blogwebsite.dto;

import by.baby.blogwebsite.enums.Role;
import lombok.Value;

import java.util.List;


@Value
public class UserDto {
    Long id;
    String username;
    String password;
    String email;
    Role role;
    String restoreKey;
    String avatar;
    Integer blogsAmount;
    List<LikeDto> likes;
    Long amountLikes;
}
