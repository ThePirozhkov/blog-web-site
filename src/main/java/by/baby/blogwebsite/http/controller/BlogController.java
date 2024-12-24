package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.BlogDto;
import by.baby.blogwebsite.dto.CreateBlogDto;
import by.baby.blogwebsite.dto.UpdateBlogDto;
import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.repository.LikeRepository;
import by.baby.blogwebsite.service.BlogService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@SessionAttributes("blog")
public class BlogController {

    private final BlogService blogService;

    private final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);
    private final LikeRepository likeRepository;

    @GetMapping("/{id}")
    public String blog(@PathVariable Long id,
                       @SessionAttribute(required = false) UserDto currentUser,
                       Model model) {
        BlogDto blog = blogService.getBlogById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        model.addAttribute("blog", blog);
        model.addAttribute("currentUser", currentUser);


        if (currentUser != null) {
            model.addAttribute("liked", likeRepository.existsByBlogIdAndUserId(id, currentUser.getId()));
        } else {
            model.addAttribute("liked", false);
        }
        return "blog/blog";
    }

    @GetMapping("/create")
    public String create(@SessionAttribute UserDto currentUser,
                         Model model) {
        model.addAttribute("currentUser", currentUser);
        return "blog/create-blog";
    }

    @PostMapping("/create")
    public String create(@SessionAttribute UserDto currentUser,
                         @ModelAttribute CreateBlogDto dto) {
        LOGGER.info("Create blog: {}", dto);
        if (!Objects.equals(currentUser.getId(), dto.getCreatorId())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        BlogDto createdBlog = blogService.createBlog(dto);
        return "redirect:/blog/" + createdBlog.getId();
    }

    @GetMapping("/update")
    public String update(@SessionAttribute UserDto currentUser,
                         @SessionAttribute BlogDto blog,
                         Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentBlog", blog);
        return "blog/update-blog";
    }

    @PostMapping("/update")
    public String update(@SessionAttribute UserDto currentUser,
                         @SessionAttribute BlogDto blog,
                         @ModelAttribute UpdateBlogDto dto) {
        if (!Objects.equals(currentUser.getId(), blog.getAuthor().getId())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        blogService.updateBlog(dto, dto.getBlogId());
        return "redirect:/blog/" + dto.getBlogId();
    }

    @GetMapping("/delete")
    public String delete(@SessionAttribute BlogDto blog,
                         @SessionAttribute UserDto currentUser,
                         Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentBlog", blog);
        return "blog/delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id,
                         @SessionAttribute BlogDto blog,
                         @SessionAttribute UserDto currentUser) {
        if (!Objects.equals(currentUser.getId(), blog.getAuthor().getId())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        blogService.deleteBlog(id);
        return "redirect:/main";
    }

}
