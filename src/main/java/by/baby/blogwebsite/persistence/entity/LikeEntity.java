package by.baby.blogwebsite.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "like_entity", schema = "public")
@ToString
public class LikeEntity {

    public LikeEntity(UserEntity user, BlogEntity blog) {
        this.user = user;
        this.blog = blog;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private BlogEntity blog;

}
