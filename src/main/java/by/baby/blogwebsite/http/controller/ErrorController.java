package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/exception")
@SessionAttributes("currentUser")
public class ErrorController {

    @GetMapping
    public String error(@ModelAttribute String error,
                        @SessionAttribute("currentUser") UserDto user,
                        Model model) {
        model.addAttribute("user", user);
        return "exception/exception";
    }
}
