package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.RegistrationDto;
import by.baby.blogwebsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);


    @GetMapping
    public String register() {
        return "registration/reg";
    }

    @PostMapping
    public String registerPost(@ModelAttribute @Validated RegistrationDto registrationDto) {
        LOGGER.info("registrationDto: {}", registrationDto);
        userService.save(registrationDto);
        return "redirect:/login?reg";
    }

}
