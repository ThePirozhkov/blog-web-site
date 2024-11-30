package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.mapper.BlogDtoMapper;
import by.baby.blogwebsite.repository.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogDtoMapper blogDtoMapper;

    public BlogService(BlogRepository blogRepository, BlogDtoMapper blogDtoMapper) {
        this.blogRepository = blogRepository;
        this.blogDtoMapper = blogDtoMapper;
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

}
