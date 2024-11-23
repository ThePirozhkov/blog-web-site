package by.baby.blogwebsite.repository;

import by.baby.blogwebsite.persistence.entity.UserEntity;
import by.baby.blogwebsite.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
