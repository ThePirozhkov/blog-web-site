package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.UpdatePassDto;
import by.baby.blogwebsite.dto.UpdateUserDto;
import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.service.ImageService;
import by.baby.blogwebsite.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@SessionAttributes("user")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;
    private final ImageService imageService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public String user(@PathVariable Long id,
                       @SessionAttribute(required = false) UserDto currentUser,
                       Model model) {
        UserDto user = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        logger.info("User with id {} found", id);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/update")
    public String updateUser(@SessionAttribute UserDto user,
                             Model model) {
        logger.info("User {} need update", user);
        model.addAttribute("updateUserDto", new UpdateUserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()));
        return "user/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute @Validated UpdateUserDto updateUserDto,
                             @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                             @SessionAttribute UserDto currentUser,
                             BindingResult bindingResult,
                             Model model) {
        if (!Objects.equals(updateUserDto.getId(), currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
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

        String password = userService.findById(updateUserDto.getId())
                .map(UserDto::getPassword)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + updateUserDto.getId() + " not found"));

        Collection<? extends GrantedAuthority> nowAuthorities = authentication.getAuthorities();
        UserDetails newUserDetails = new User(updateUserDto.getUsername(), password, nowAuthorities);
        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(newUserDetails, authentication.getCredentials(), nowAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/user/" + updateUserDto.getId() + "?upd";
    }

    @GetMapping("/update-password")
    public String updatePass(@SessionAttribute UserDto user,
                             Model model) {
        model.addAttribute("updatePassDto", new UpdatePassDto(
                user.getId(),
                null,
                null,
                null
        ));
        model.addAttribute("hasOldPassError", false);
        model.addAttribute("hasNewPassError", false);
        model.addAttribute("hasConfirmPassError", false);
        return "user/update-password";
    }

    @PostMapping("/update-password")
    public String updatePass(@ModelAttribute @Validated UpdatePassDto updatePassDto,
                             @SessionAttribute UserDto currentUser,
                             BindingResult bindingResult,
                             Model model) {
        if (!Objects.equals(updatePassDto.getId(), currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        List<String> errors = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getCode)
                .toList();
        if (bindingResult.hasErrors()) {
            model.addAttribute("hasOldPassError", errors.contains("CheckOldPassword"));
            model.addAttribute("hasNewPassError", errors.contains("CheckNewPassword"));
            model.addAttribute("hasConfirmPassError", errors.contains("ConfirmPasswordUPD"));
            return "user/update-password";
        }
        userService.updatePass(updatePassDto, updatePassDto.getId());
        return "redirect:/user/" + updatePassDto.getId() + "?upd";
    }

    @GetMapping("/update-image")
    public String updateImage(@SessionAttribute UserDto currentUser,
                              Model model) {
        model.addAttribute("user", currentUser);
        return "user/update-image";
    }


    @PostMapping("/update-image")
    public String updateImage(@RequestParam MultipartFile avatar,
                              @SessionAttribute UserDto currentUser) {
        try {
            imageService.saveAvatar(
                    avatar.getBytes(),
                    "avatar-" + currentUser.getId() + "." + FilenameUtils.getExtension(avatar.getOriginalFilename()),
                    currentUser.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/user/" + currentUser.getId() + "?updimg";
    }

    @GetMapping("/delete")
    public String deleteUser(@SessionAttribute UserDto currentUser,
                             @SessionAttribute UserDto user,
                             Model model) {
        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        return "user/delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id,
                             @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                             @SessionAttribute UserDto currentUser,
                             HttpServletResponse httpServletResponse,
                             HttpServletRequest httpServletRequest) {
        if (!Objects.equals(id, currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            httpSession.removeAttribute("currentUser");
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
            return "redirect:/logout";
        } else {
            return "redirect:/user/" + id;
        }
    }

}
