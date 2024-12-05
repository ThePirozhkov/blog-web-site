package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.NewLikeDto;
import by.baby.blogwebsite.service.LikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/like")
public class LikeController {

    Logger logger = LoggerFactory.getLogger(LikeController.class);

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public String like(NewLikeDto newLikeDto) {
        logger.info("Like request received: {}", newLikeDto);
        likeService.saveLike(newLikeDto);
        return "redirect:/blog/" + newLikeDto.getBlogId();
    }

    @PostMapping("/unlike")
    public String unlike(NewLikeDto newLikeDto) {
        logger.info("Unlike request received: {}", newLikeDto);
        likeService.deleteLike(newLikeDto);
        return "redirect:/blog/" + newLikeDto.getBlogId();
    }

}
