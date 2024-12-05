package by.baby.blogwebsite.dto;

import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class BlogDto {
    Long id;
    String title;
    String content;
    Date createdAt;
    UserDto author;
    List<LikeDto> likes;
}
