package by.baby.blogwebsite.repository;

import by.baby.blogwebsite.persistence.entity.BlogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    Page<BlogEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<BlogEntity> findAllByPopular(boolean popular);
    boolean existsByIdAndPopular(Long id, boolean popular);
}
