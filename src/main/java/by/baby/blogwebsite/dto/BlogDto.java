package by.baby.blogwebsite.dto;

import lombok.Value;

import java.util.Date;

@Value
public class BlogDto {
    Long id;
    String title;
    String content;
    Date createdAt;
    UserDto author;
}
