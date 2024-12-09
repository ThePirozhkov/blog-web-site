package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.service.BlogService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainPageController {

    private final BlogService blogService;
    private final HttpSession httpSession;


    @GetMapping
    public String mainPage(@RequestParam(defaultValue = "0") int page,
                           @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                           Model model) {
        if (httpSession.getAttribute("currentUser") == null && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));
        model.addAttribute("blogs", blogService.getBlogs(PageRequest.of(page, 12)));
        return "main/main";
    }

}
