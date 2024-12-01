package by.baby.blogwebsite.http.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/blog")
@SessionAttributes({"user"})
public class BlogController {

    private final HttpSession httpSession;

    public BlogController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/{id}")
    public String blog(@PathVariable String id,
                       Model model) {
        model.addAttribute("user", httpSession.getAttribute("user"));
        return "blog/blog";
    }

}
