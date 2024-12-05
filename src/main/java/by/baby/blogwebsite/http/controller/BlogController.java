package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.dto.CreateBlogDto;
import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.repository.BlogRepository;
import by.baby.blogwebsite.repository.LikeRepository;
import by.baby.blogwebsite.repository.UserRepository;
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

    private final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);
    private final LikeRepository likeRepository;

    public BlogController(HttpSession httpSession, BlogService blogService, LikeRepository likeRepository, UserRepository userRepository) {
        this.httpSession = httpSession;
        this.blogService = blogService;
        this.likeRepository = likeRepository;
    }

    @GetMapping("/{id}")
    public String blog(@PathVariable Long id,
                       Model model) {
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");
        BlogDto currentBlog = blogService.getBlogById(id);

        model.addAttribute("blog", currentBlog);
        model.addAttribute("currentUser", currentUser);

        if (currentUser != null) {
            model.addAttribute("liked", likeRepository.existsByBlogIdAndUserId(id, currentUser.getId()));
        } else {
            model.addAttribute("liked", false);
        }
        return "blog/blog";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));
        return "blog/create-blog";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CreateBlogDto dto) {
        LOGGER.info("Create blog: {}", dto);
        BlogDto createdBlog = blogService.createBlog(dto);
        return "redirect:/blog/" + createdBlog.getId();
    }

}
