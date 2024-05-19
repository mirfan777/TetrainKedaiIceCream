package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu_comments")
@Data
public class MenuComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

    @NotBlank(message = "name is required")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "body is required")
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_reply_id", referencedColumnName = "id")
    private MenuReply menuReply;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    
}
