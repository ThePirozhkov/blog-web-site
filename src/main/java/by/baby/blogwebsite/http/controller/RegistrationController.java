package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.RegistrationDto;
import by.baby.blogwebsite.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @GetMapping
    public String register(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "registration/reg";
    }

    @PostMapping
    public String registerPost(@ModelAttribute @Validated RegistrationDto registrationDto,
                               BindingResult bindingResult,
                               Model model) {
        LOGGER.info("registrationDto: {}", registrationDto);
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationDto", registrationDto);
            return "registration/reg";
        }
        userService.save(registrationDto);
        return "redirect:/login?reg";
    }

}
