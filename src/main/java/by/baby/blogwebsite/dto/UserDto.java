package by.baby.blogwebsite.dto;

import by.baby.blogwebsite.enums.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDto implements Serializable {
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
    Date createdAt;
}
