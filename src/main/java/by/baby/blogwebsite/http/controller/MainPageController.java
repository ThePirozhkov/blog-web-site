package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.persistence.entity.UserEntity;
import by.baby.blogwebsite.repository.UserRepository;
import by.baby.blogwebsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainPageController {

    private final UserService userService;

    @GetMapping
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        UserDto user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("user", user);
        return "main/main";
    }

}
