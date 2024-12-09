package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.NewLikeDto;
import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.service.LikeService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Controller
@RequestMapping("/like")
public class LikeController {

    Logger logger = LoggerFactory.getLogger(LikeController.class);

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public String like(NewLikeDto newLikeDto,
                       HttpSession session) {
        logger.info("Like request received: {}", newLikeDto);
        UserDto currentUser = (UserDto) session.getAttribute("currentUser");
        if (!Objects.equals(newLikeDto.getUserId(), currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        likeService.saveLike(newLikeDto);
        return "redirect:/blog/" + newLikeDto.getBlogId();
    }

    @PostMapping("/unlike")
    public String unlike(NewLikeDto newLikeDto,
                         HttpSession session) {
        logger.info("Unlike request received: {}", newLikeDto);
        UserDto currentUser = (UserDto) session.getAttribute("currentUser");
        if (!Objects.equals(newLikeDto.getUserId(), currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        likeService.deleteLike(newLikeDto);
        return "redirect:/blog/" + newLikeDto.getBlogId();
    }

}
