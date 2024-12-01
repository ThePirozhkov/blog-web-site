package by.baby.blogwebsite.rest.controller;

import by.baby.blogwebsite.service.ImageService;
import by.baby.blogwebsite.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/load-image/{filename:.+}")
    public ResponseEntity<Resource> loadImage(@PathVariable String filename) {
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(filename)))
                    .header(HttpHeaders.CACHE_CONTROL, HeaderUtil.getMaxAgeHeader(2))
                    .header(HttpHeaders.PRAGMA, "public")
                    .header(HttpHeaders.EXPIRES, HeaderUtil.getExpiresHeader(2))
                    .body(imageService.loadAvatar(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
