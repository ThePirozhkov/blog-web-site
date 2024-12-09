package by.baby.blogwebsite.dto;

import lombok.Value;

@Value
public class UpdateBlogDto {
    Long blogId;
    String title;
    String content;
}
