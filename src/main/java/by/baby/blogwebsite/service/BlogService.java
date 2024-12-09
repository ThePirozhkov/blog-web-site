package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.dto.CreateBlogDto;
import by.baby.blogwebsite.dto.UpdateBlogDto;
import by.baby.blogwebsite.mapper.BlogDtoMapper;
import by.baby.blogwebsite.persistence.entity.BlogEntity;
import by.baby.blogwebsite.repository.BlogRepository;
import by.baby.blogwebsite.repository.UserRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogDtoMapper blogDtoMapper;
    private final UserRepository userRepository;

    public BlogService(BlogRepository blogRepository, BlogDtoMapper blogDtoMapper, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.blogDtoMapper = blogDtoMapper;
        this.userRepository = userRepository;
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

    public Optional<BlogDto> getBlogById(Long id) {
        return blogRepository.findById(id)
                .map(blogDtoMapper::mapToBlogDto);
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

    public BlogDto updateBlog(UpdateBlogDto blogDto, Long id) {
        return blogRepository.findById(id)
                .map(blogEntity -> {
                    blogEntity.setTitle(blogDto.getTitle());
                    blogEntity.setContent(blogDto.getContent());
                    return blogEntity;
                })
                .map(blogRepository::save)
                .map(blogDtoMapper::mapToBlogDto)
                .orElseThrow(() -> new RuntimeException("Cannot update blog"));
    }

}
