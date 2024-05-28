package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private MenuReview menuReview;

    @ManyToOne
    @JoinColumn(name = "replied_by", referencedColumnName = "id")
    private User user;

    @NotBlank(message = "body is required")
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    
}
