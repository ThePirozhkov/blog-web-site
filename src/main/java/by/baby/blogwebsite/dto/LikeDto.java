package by.baby.blogwebsite.dto;

import lombok.Value;

@Value
public class LikeDto {
    Long id;
    Long blogId;
    Long userId;
}
