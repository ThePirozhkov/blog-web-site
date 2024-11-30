package by.baby.blogwebsite.repository;

import by.baby.blogwebsite.persistence.entity.BlogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    Page<BlogEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
