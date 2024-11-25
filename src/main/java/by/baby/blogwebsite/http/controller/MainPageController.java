package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.persistence.entity.UserEntity;
import by.baby.blogwebsite.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainPageController {

    private final UserRepository userRepository;

    public MainPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("user", user);
        return "main/main";
    }

}
