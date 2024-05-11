package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "blogs")
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "image_files", referencedColumnName = "id" , nullable = true)
    private ImageFile imageFiles;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "json")
    private Object content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
