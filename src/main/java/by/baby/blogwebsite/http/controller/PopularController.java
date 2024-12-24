package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.service.cache.BlogCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
@RequiredArgsConstructor
public class PopularController {

    private final BlogCacheService blogCacheService;

    @PostMapping("/pop/{id}")
    public String pop(@PathVariable Long id) {
        blogCacheService.addInCacheBlog(id);
        return "redirect:/blog/" + id;
    }

    @PostMapping("/delpop/{id}")
    public String del(@PathVariable Long id) {
        blogCacheService.deleteInCacheBlog(id);
        return "redirect:/blog/" + id;
    }

}
