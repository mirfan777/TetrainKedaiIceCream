package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu_replies")
@Data
public class MenuReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "menuReply")
    private MenuComment menuComment;

    @ManyToOne
    @JoinColumn(name = "replied_by", referencedColumnName = "id")
    private User user;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    
}
