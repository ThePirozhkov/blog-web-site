package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.dto.CreateBlogDto;
import by.baby.blogwebsite.dto.UpdateBlogDto;
import by.baby.blogwebsite.mapper.BlogDtoMapper;
import by.baby.blogwebsite.persistence.entity.BlogEntity;
import by.baby.blogwebsite.repository.BlogRepository;
import by.baby.blogwebsite.repository.UserRepository;
import by.baby.blogwebsite.service.cache.BlogCacheService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "blogCache")
@Slf4j
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogDtoMapper blogDtoMapper;
    private final UserRepository userRepository;
    private final BlogCacheService blogCacheService;

    @PostConstruct
    void init() {
        log.info("blogRepo : {}", blogRepository);
    }


    public PageImpl<BlogDto> getBlogs(Pageable pageable) {
        return Optional.ofNullable(blogRepository.findAllByOrderByCreatedAtDesc(pageable))
                .map(page -> {
                    List<BlogDto> blogDtos = page.getContent()
                            .stream()
                            .map(blogDtoMapper::mapToBlogDto)
                            .toList();
                    return new PageImpl<>(blogDtos, pageable, page.getTotalElements());
                })
                .orElseThrow(() -> new RuntimeException("Unable to find any blogs"));
    }

    @CacheEvict(condition = "@blogRepository.existsByIdAndPopular(#id, false)")
    @Cacheable(unless = "@blogRepository.existsByIdAndPopular(#id, false)")
    public Optional<BlogDto> getBlogById(Long id) {
        BlogDto blogDto = blogRepository.findById(id)
                .map(blogDtoMapper::mapToBlogDto)
                .orElseThrow(() -> new RuntimeException("Unable to find blog by id: " + id));
        return Optional.of(blogDto);
    }

    public BlogDto createBlog(CreateBlogDto blogDto) {
        return Optional.of(blogDto)
                .map(dto -> new BlogEntity(
                        userRepository.findById(dto.getCreatorId()).orElseThrow(),
                        blogDto.getContent(),
                        blogDto.getTitle()
                ))
                .map(blogEntity -> {
                    blogEntity.getCreator().getBlogs().add(blogEntity);
                    return blogEntity;
                })
                .map(blogRepository::save)
                .map(blogDtoMapper::mapToBlogDto)
                .orElseThrow(() -> new RuntimeException("Cannot create blog"));
    }

    @CachePut(key = "#id")
    public BlogDto updateBlog(UpdateBlogDto blogDto, Long id) {
        BlogDto blog = blogRepository.findById(id)
                .map(blogEntity -> {
                    blogEntity.setTitle(blogDto.getTitle());
                    blogEntity.setContent(blogDto.getContent());
                    return blogEntity;
                })
                .map(blogRepository::save)
                .map(blogDtoMapper::mapToBlogDto)
                .orElseThrow(() -> new RuntimeException("Cannot update blog"));
        blogCacheService.updInCacheBlogs();
        return blog;
    }

    @CacheEvict(key = "#id")
    public boolean deleteBlog(Long id) {
        blogRepository.deleteById(id);
        return true;
    }

}
