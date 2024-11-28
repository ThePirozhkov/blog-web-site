package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.mapper.UserDtoMapper;
import by.baby.blogwebsite.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
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
        String AVATAR_PATH = "src/main/resources/static/images/avatars";
        File file = new File(AVATAR_PATH + "/" + filename);
        if (file.exists()) {
            File tempFile = new File(filename);
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(avatar);
                fos.flush();
            }
            Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(avatar);
                fileOutputStream.flush();
            }
        }

        return userRepository.findById(id)
                .map(userEntity -> {
                    userEntity.setAvatar("images/avatars/" + filename);
                    return userEntity;
                })
                .map(userRepository::saveAndFlush)
                .map(userDtoMapper::mapToUserDto)
                .orElseThrow(() -> new RuntimeException("Cannot save image " + filename));
    }

}
