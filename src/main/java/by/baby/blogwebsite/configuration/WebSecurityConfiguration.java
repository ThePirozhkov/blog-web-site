package by.baby.blogwebsite.configuration;

import by.baby.blogwebsite.enums.Role;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private final DataSource dataSource;

    public WebSecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/login/**", "/logout", "/reg", "/restore", "/image/**", "/exception", "/main", "/user/**", "/blog/**").permitAll()
                                .requestMatchers("/like", "/like/unlike").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                                .requestMatchers("/pop/**", "/delpop/**").hasAuthority(Role.ADMIN.getAuthority()))
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/login/verification", true)
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/login"))
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionFixation().migrateSession()
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/login"))
                .rememberMe(rememberMe ->
                        rememberMe
                                .rememberMeParameter("remember-me")
                                .tokenRepository(persistentTokenRepository())
                                .tokenValiditySeconds(30 * 24 * 60 * 60));
        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
