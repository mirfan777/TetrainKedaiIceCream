package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    private Integer role;

    @Column(name = "token", nullable = true, unique = true)
    private String token;

    @Column(name = "token_expired_at", nullable = true)
    private Integer tokenExpiredAt;

}