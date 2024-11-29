package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.mapper.UserDtoMapper;
import by.baby.blogwebsite.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public ImageService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @SneakyThrows
    public UserDto saveAvatar(byte[] avatar, String filename, Long id) {
        Path AVATAR_PATH = Paths.get(System.getProperty("user.dir"), "uploads", filename);
        File file = AVATAR_PATH.toFile();
        if (file.exists()) {
            File tempFile = new File(filename);
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile))) {
                bos.write(avatar);
                bos.flush();
            }
            Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                bos.write(avatar);
                bos.flush();
            }
        }

        return userRepository.findById(id)
                .map(userEntity -> {
                    userEntity.setAvatar(filename);
                    return userEntity;
                })
                .map(userRepository::saveAndFlush)
                .map(userDtoMapper::mapToUserDto)
                .orElseThrow(() -> new RuntimeException("Cannot save image " + filename));
    }

    public Resource loadAvatar(String filename) {

        final Path AVATAR_PATH = Paths.get(System.getProperty("user.dir"), "uploads", filename);
        return new FileSystemResource(AVATAR_PATH.toFile());

    }

}
