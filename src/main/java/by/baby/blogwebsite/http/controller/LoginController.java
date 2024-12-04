package by.baby.blogwebsite.http.controller;

import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/login")
@SessionAttributes({"currentUser"})
@RequiredArgsConstructor
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping
    public String login(@CurrentSecurityContext(expression = "authentication") Authentication authentication,
                        @AuthenticationPrincipal UserDetails user) {
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            logger.info("auth: {}", authentication);
            if (httpSession.getAttribute("currentUser") == null) {
                httpSession.setAttribute("currentUser", userService.findByUsername(user.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException(user.getUsername())));
            }
            return "redirect:/main";
        }
        return "login/login";
    }

    @GetMapping("/verification")
    public String verification(@AuthenticationPrincipal UserDetails userDetails,
                               @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return "redirect:/login";
        }
        if (userDetails != null) {
            UserDto currentUser = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            httpSession.setAttribute("currentUser", currentUser);
        }
        return "redirect:/main?login";
    }

}
