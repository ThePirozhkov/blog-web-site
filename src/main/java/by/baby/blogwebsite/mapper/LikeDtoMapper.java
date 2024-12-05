package by.baby.blogwebsite.mapper;

import by.baby.blogwebsite.dto.LikeDto;
import by.baby.blogwebsite.dto.NewLikeDto;
import by.baby.blogwebsite.persistence.entity.LikeEntity;
import by.baby.blogwebsite.repository.BlogRepository;
import by.baby.blogwebsite.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class LikeDtoMapper {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public LikeDtoMapper(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public LikeDto mapToDto(LikeEntity likeEntity) {
        return new LikeDto(
                likeEntity.getId(),
                likeEntity.getBlog().getId(),
                likeEntity.getUser().getId());
    }

    public LikeEntity mapToEntity(LikeDto likeDto) {
        return new LikeEntity(
                likeDto.getId(),
                userRepository.findById(likeDto.getUserId()).orElseThrow(),
                blogRepository.findById(likeDto.getBlogId()).orElseThrow());
    }

    public LikeEntity mapToEntity(NewLikeDto newLikeDto) {
        return new LikeEntity(
                userRepository.findById(newLikeDto.getUserId()).orElseThrow(),
                blogRepository.findById(newLikeDto.getBlogId()).orElseThrow()
        );
    }

}
