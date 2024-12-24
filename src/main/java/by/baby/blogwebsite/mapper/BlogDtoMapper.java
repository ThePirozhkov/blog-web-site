package by.baby.blogwebsite.mapper;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.persistence.entity.BlogEntity;
import by.baby.blogwebsite.repository.BlogRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BlogDtoMapper {

    private final UserDtoMapper userDtoMapper;
    private final BlogRepository blogRepository;
    private final LikeDtoMapper likeDtoMapper;

    public BlogDtoMapper(UserDtoMapper userDtoMapper, BlogRepository blogRepository, LikeDtoMapper likeDtoMapper) {
        this.userDtoMapper = userDtoMapper;
        this.blogRepository = blogRepository;
        this.likeDtoMapper = likeDtoMapper;
    }

    public BlogDto mapToBlogDto(BlogEntity blogEntity) {
        return Optional.of(new BlogDto(
                blogEntity.getId(),
                blogEntity.getTitle(),
                blogEntity.getContent(),
                blogEntity.getCreatedAt(),
                userDtoMapper.mapToUserDto(blogEntity.getCreator()),
                blogEntity.getLikes().stream().map(likeDtoMapper::mapToDto).toList(),
                blogEntity.isPopular()
        ))
                .orElseThrow(() -> new RuntimeException("Blog not mapped"));
    }

    public BlogEntity mapToBlogEntity(BlogDto blogDto) {
        return blogRepository.findById(blogDto.getId())
                .orElseThrow(() -> new RuntimeException("Blog not mapped"));
    }
}
