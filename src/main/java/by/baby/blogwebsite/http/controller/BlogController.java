package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.dto.CreateBlogDto;
import by.baby.blogwebsite.dto.UpdateBlogDto;
import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.repository.BlogRepository;
import by.baby.blogwebsite.repository.LikeRepository;
import by.baby.blogwebsite.service.BlogService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@Controller
@RequestMapping("/blog")
@SessionAttributes({"user"})
public class BlogController {

    private final HttpSession httpSession;
    private final BlogService blogService;

    private final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);
    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;

    public BlogController(HttpSession httpSession, BlogService blogService, LikeRepository likeRepository, BlogRepository blogRepository) {
        this.httpSession = httpSession;
        this.blogService = blogService;
        this.likeRepository = likeRepository;
        this.blogRepository = blogRepository;
    }

    @GetMapping("/{id}")
    public String blog(@PathVariable Long id,
                       Model model) {
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");
        BlogDto currentBlog = blogService.getBlogById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        model.addAttribute("blog", currentBlog);
        model.addAttribute("currentUser", currentUser);

        httpSession.setAttribute("blog", currentBlog);

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

    @GetMapping("/update")
    public String update(Model model) {
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");
        BlogDto currentBlog = (BlogDto) httpSession.getAttribute("blog");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentBlog", currentBlog);
        return "blog/update-blog";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute UpdateBlogDto dto) {
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");
        BlogDto currentBlog = (BlogDto) httpSession.getAttribute("blog");
        if (!Objects.equals(currentUser.getId(), currentBlog.getAuthor().getId())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        blogService.updateBlog(dto, dto.getBlogId());
        return "redirect:/blog/" + dto.getBlogId();
    }

}
