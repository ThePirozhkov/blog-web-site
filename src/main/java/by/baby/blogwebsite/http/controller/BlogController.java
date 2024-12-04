package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.dto.CreateBlogDto;
import by.baby.blogwebsite.repository.BlogRepository;
import by.baby.blogwebsite.service.BlogService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blog")
@SessionAttributes({"user"})
public class BlogController {

    private final HttpSession httpSession;
    private final BlogService blogService;
    private final BlogRepository blogRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    public BlogController(HttpSession httpSession, BlogService blogService, BlogRepository blogRepository) {
        this.httpSession = httpSession;
        this.blogService = blogService;
        this.blogRepository = blogRepository;
    }

    @GetMapping("/{id}")
    public String blog(@PathVariable Long id,
                       Model model) {
        BlogDto currentBlog = blogService.getBlogById(id);
        model.addAttribute("blog", currentBlog);
        model.addAttribute("blogsAmount", blogRepository.countByCreatorId(currentBlog.getAuthor().getId()));
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));
        return "blog/blog";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));
        return "blog/create-blog";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CreateBlogDto dto,
                         Model model) {
        LOGGER.info("Create blog: {}", dto);
        BlogDto createdBlog = blogService.createBlog(dto);
        return "redirect:/blog/" + createdBlog.getId();
    }

}
