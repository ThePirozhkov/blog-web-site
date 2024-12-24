package by.baby.blogwebsite.service.cache;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.mapper.BlogDtoMapper;
import by.baby.blogwebsite.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "blogCache")
public class BlogCacheService {

    private final BlogRepository blogRepository;
    private final BlogDtoMapper blogDtoMapper;

    @Cacheable(key = "'popBlogs'")
    public List<BlogDto> getAllInCacheBlogs() {
        return getBlogs();
    }

    @CachePut(key = "'popBlogs'")
    public List<BlogDto> updInCacheBlogs() {
        return getBlogs();
    }

    @CachePut(key = "'popBlogs'", unless = "#result == null")
    public List<BlogDto> addInCacheBlog(Long id) {
        blogRepository.findById(id).ifPresent(blog -> {
            blog.setPopular(true);
            blogRepository.saveAndFlush(blog);
        });
        return getBlogs();
    }

    @CachePut(key = "'popBlogs'")
    public List<BlogDto> deleteInCacheBlog(Long id) {
        blogRepository.findById(id).ifPresent(blog -> {
            blog.setPopular(false);
            blogRepository.saveAndFlush(blog);
        });
        return getBlogs();
    }

    private List<BlogDto> getBlogs() {
        ArrayList<BlogDto> blogs = new java.util.ArrayList<>(blogRepository.findAllByPopular(true)
                .stream()
                .map(blogDtoMapper::mapToBlogDto)
                .toList());
        Collections.reverse(blogs);
        return blogs;
    }

}
