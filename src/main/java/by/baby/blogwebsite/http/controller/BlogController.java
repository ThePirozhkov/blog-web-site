package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.service.BlogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/blog")
@SessionAttributes({"user"})
public class BlogController {

    private final HttpSession httpSession;
    private final BlogService blogService;

    public BlogController(HttpSession httpSession, BlogService blogService) {
        this.httpSession = httpSession;
        this.blogService = blogService;
    }

    @GetMapping("/{id}")
    public String blog(@PathVariable Long id,
                       Model model) {
        BlogDto currentBlog = blogService.getBlogById(id);
        model.addAttribute("blog", currentBlog);
        model.addAttribute("user", httpSession.getAttribute("user"));
        return "blog/blog";
    }

}
