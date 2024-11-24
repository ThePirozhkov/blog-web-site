package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.RestoreAccessDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/restore")
public class RestorePasswordController {

    Logger LOGGER = LoggerFactory.getLogger(RestorePasswordController.class);

    @GetMapping
    public String restorePassword() {
        return "login/restore_password";
    }

    @PostMapping
    public String restorePassword(@ModelAttribute @Validated RestoreAccessDto restoreAccessDto,
                                  BindingResult bindingResult,
                                  Model model) {
        List<String> errors = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getCode)
                .toList();
        if (bindingResult.hasErrors()) {
            model.addAttribute("hasCheckRestoreDataError", errors.contains("CheckRestoreData"));
            LOGGER.error("Error: {}", bindingResult);
            return "login/restore_password";
        }
        return "redirect:/login/login";
    }

}
