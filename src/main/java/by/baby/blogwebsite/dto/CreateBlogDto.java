package by.baby.blogwebsite.dto;

import lombok.Value;

@Value
public class CreateBlogDto {
    Long creatorId;
    String title;
    String content;
}
