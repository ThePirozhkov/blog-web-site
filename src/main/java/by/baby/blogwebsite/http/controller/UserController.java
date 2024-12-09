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
@SessionAttributes({"user", "currentUser"})
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;
    private final ImageService imageService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public String user(@PathVariable Long id,
                       Model model) {
        UserDto user = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        logger.info("User with id {} found", id);
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));
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
                             @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                             BindingResult bindingResult,
                             Model model) {
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");
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
    public String updatePass(Model model) {
        UserDto updateUser = (UserDto) httpSession.getAttribute("user");
        model.addAttribute("updatePassDto", new UpdatePassDto(
                updateUser.getId(),
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
                             BindingResult bindingResult,
                             Model model) {
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");
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
    public String updateImage(Model model) {
        UserDto updateUser = (UserDto) httpSession.getAttribute("user");
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");
        if (!Objects.equals(updateUser.getId(), currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        model.addAttribute("user", updateUser);
        return "user/update-image";
    }


    @PostMapping("/update-image")
    public String updateImage(
            @RequestParam MultipartFile avatar) {
        UserDto updateUser = (UserDto) httpSession.getAttribute("user");
        try {
            imageService.saveAvatar(
                    avatar.getBytes(),
                    "avatar-" + updateUser.getId() + "." + FilenameUtils.getExtension(avatar.getOriginalFilename()),
                    updateUser.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/user/" + updateUser.getId() + "?updimg";
    }

    @GetMapping("/delete")
    public String deleteUser(Model model) {
        UserDto deleteUser = (UserDto) httpSession.getAttribute("user");
        model.addAttribute("user", deleteUser);
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));
        return "user/delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id,
                             @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                             HttpServletResponse httpServletResponse,
                             HttpServletRequest httpServletRequest) {
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");
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
