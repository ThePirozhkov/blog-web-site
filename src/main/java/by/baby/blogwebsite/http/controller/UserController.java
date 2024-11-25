package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.UpdateUserDto;
import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@SessionAttributes({"user"})
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public String user(@PathVariable Long id,
                       Model model) {
        UserDto user = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        logger.info("User with id {} found", id);
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/update")
    public String updateUser(Model model) {
        UserDto updateUser = (UserDto) httpSession.getAttribute("user");
        logger.info("User {} need update", updateUser);
        model.addAttribute("updateUserDto", new UpdateUserDto(
                updateUser.getId(),
                updateUser.getUsername(),
                updateUser.getEmail()));
        return "user/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute @Validated UpdateUserDto updateUserDto,
                             BindingResult bindingResult,
                             Model model) {
        List<String> errors = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getCode)
                .toList();
        if (bindingResult.hasErrors()) {
            model.addAttribute("hasUniqueEmailError", errors.contains("UniqueEmailUPD"));
            model.addAttribute("updateUserDto", updateUserDto);
            return "user/update";
        }
        userService.updateUser(updateUserDto, updateUserDto.getId());
        return "redirect:/login?upd";
    }

}
