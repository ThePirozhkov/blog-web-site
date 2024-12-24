package by.baby.blogwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LikeDto implements Serializable {
    Long id;
    Long blogId;
    Long userId;
}
