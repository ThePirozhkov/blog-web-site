package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exception")
public class ErrorController {

    private final HttpSession httpSession;

    public ErrorController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping
    public String error(@ModelAttribute String error, Model model) {
        UserDto user = (UserDto) httpSession.getAttribute("user");
        model.addAttribute("user", user);
        return "exception/exception";
    }
}
