package by.baby.blogwebsite.mapper;

import by.baby.blogwebsite.dto.UserDto;
import by.baby.blogwebsite.persistence.entity.UserEntity;
import by.baby.blogwebsite.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDtoMapper {

    private final UserRepository userRepository;
    private final LikeDtoMapper likeDtoMapper;

    @Getter
    private static class AmountLikes {
        private Long count = 0L;

        public void add(int amount) {
            this.count += amount;
        }
    }

    public UserDto mapToUserDto(UserEntity user) {

        AmountLikes amountLikes = new AmountLikes();

        user
                .getBlogs()
                .forEach(blog -> amountLikes.add(blog.getLikes().size()));

        return Optional.of(new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRole(),
                        user.getRestoreKey(),
                        user.getAvatar(),
                        user.getBlogs().size(),
                        user.getLikes().stream().map(likeDtoMapper::mapToDto).toList(),
                        amountLikes.getCount(),
                        user.getCreatedAt()))
                .orElseThrow(() -> new RuntimeException("User not mapped"));
    }

    public UserEntity mapToUserEntity(UserDto userDto) {
        return userRepository.findById(userDto.getId())
                .orElse(null);
    }
}
