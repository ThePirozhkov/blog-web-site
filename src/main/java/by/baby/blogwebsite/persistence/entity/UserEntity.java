package by.baby.blogwebsite.persistence.entity;

import by.baby.blogwebsite.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_entity", schema = "public")
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "restorekey", nullable = false, unique = true)
    private String restoreKey;

    @Column(name = "avatar")
    private String avatar;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BlogEntity> blogs;

    public UserEntity(Role role, String email, String password, String username) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public UserEntity(Role role, String email, String password, String username, String avatar) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.username = username;
        this.avatar = avatar;
    }

    @PrePersist
    protected void onCreate() {
        this.restoreKey = UUID.randomUUID().toString();
    }
}
