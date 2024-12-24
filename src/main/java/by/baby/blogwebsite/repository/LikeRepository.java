package by.baby.blogwebsite.repository;

import by.baby.blogwebsite.persistence.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByBlogIdAndUserId(Long blogId, Long userId);
    boolean existsByBlogIdAndUserId(Long blogId, Long userId);

}
