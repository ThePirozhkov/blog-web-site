package by.baby.blogwebsite.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "blog_entity", schema = "public")
public class BlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "content", nullable = false, length = 10000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "popular", nullable = false)
    private boolean popular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<LikeEntity> likes;

    @PrePersist
    private void setCreatedAt() {
        this.createdAt = new Date();
    }

    public BlogEntity(UserEntity creator, String content, String title) {
        this.creator = creator;
        this.content = content;
        this.title = title;
    }
}
