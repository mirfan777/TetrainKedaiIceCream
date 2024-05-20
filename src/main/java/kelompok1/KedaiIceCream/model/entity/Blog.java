package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnTransformer;

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
    @NotNull(message = "category is required")
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private BlogCategory category;

    @Column(name = "image")
    private String image;

    @NotBlank(message = "title is required")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "description is required")
    @Column(name = "description")
    private String description;

    @NotNull(message = "content is required")
    @ColumnTransformer(write = "?::jsonb")
    @Column(name = "content", columnDefinition = "jsonb")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
