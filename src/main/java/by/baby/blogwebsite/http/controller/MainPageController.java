package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.service.BlogService;
import by.baby.blogwebsite.util.HeaderUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
                           HttpServletResponse response,
                           Model model) {
        response.addHeader("Expires", HeaderUtil.getExpiresHeader(2));
        response.addHeader("Pragma", "public");
        response.addHeader("Cache-Control", HeaderUtil.getMaxAgeHeader(2));
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));
        model.addAttribute("blogs", blogService.getBlogs(PageRequest.of(page, 14)));
        return "main/main";
    }

}
