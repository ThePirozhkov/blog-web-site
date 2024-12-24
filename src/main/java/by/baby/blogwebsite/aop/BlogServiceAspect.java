package by.baby.blogwebsite.aop;

import by.baby.blogwebsite.service.cache.BlogCacheService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BlogServiceAspect {

    private final BlogCacheService blogCacheService;

    public BlogServiceAspect(BlogCacheService blogCacheService) {
        this.blogCacheService = blogCacheService;
    }

    @Pointcut("execution(public * by.baby.blogwebsite.service.BlogService.deleteBlog(Long))")
    public void isBlogServiceDeleteBlogMethod() {
    }

    @AfterReturning(value = "isBlogServiceDeleteBlogMethod()")
    public void doAfterReturning() {
        blogCacheService.updInCacheBlogs();
    }
}
