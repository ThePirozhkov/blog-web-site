package by.baby.blogwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BlogWebSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogWebSiteApplication.class, args);
    }

}
