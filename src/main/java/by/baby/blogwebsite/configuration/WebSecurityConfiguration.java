package by.baby.blogwebsite.configuration;

import by.baby.blogwebsite.enums.Role;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/login", "/logout", "/reg").permitAll()
                                .requestMatchers("/main").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority()))
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/main?login", true)
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }

}
