package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.service.BlogService;
import by.baby.blogwebsite.util.HeaderUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
@SessionAttributes({"currentUser"})
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
        model.addAttribute("blogs", blogService.getBlogs(PageRequest.of(page, 14)));
        return "main/main";
    }

}
