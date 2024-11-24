package by.baby.blogwebsite.persistence.entity;

import by.baby.blogwebsite.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_entity", schema = "public")
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

    public UserEntity(Role role, String email, String password, String username) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    @PrePersist
    protected void onCreate() {
        this.restoreKey = UUID.randomUUID().toString();
    }
}
