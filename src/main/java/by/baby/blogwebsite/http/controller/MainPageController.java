package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.service.BlogService;
import by.baby.blogwebsite.service.UserService;
import by.baby.blogwebsite.util.HeaderUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
@SessionAttributes({"user"})
public class MainPageController {

    private final UserService userService;
    private final BlogService blogService;

    @GetMapping
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam(defaultValue = "0") int page,
                           HttpServletResponse response,
                           Model model) {
        UserDto user = null;
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        response.addHeader("Expires", HeaderUtil.getExpiresHeader(2));
        response.addHeader("Pragma", "public");
        response.addHeader("Cache-Control", HeaderUtil.getMaxAgeHeader(2));
        model.addAttribute("user", user);
        model.addAttribute("blogs", blogService.getBlogs(PageRequest.of(page, 14)));
        return "main/main";
    }

}
