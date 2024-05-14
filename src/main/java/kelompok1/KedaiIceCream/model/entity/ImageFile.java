package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "admin_image_repositories")
@Data
public class ImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotBlank(message = "image is required")
    @Column(name = "image", length = Integer.MAX_VALUE)
    private byte[] image;

    @NotBlank(message = "title is required")
    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "imageFiles")
    private List<Menu> menu;

    @OneToMany(mappedBy = "imageFiles")
    private List<MenuVariant> menuVariant;

    @OneToMany(mappedBy = "imageFiles")
    private List<MenuAdding> menuAdding;

    @OneToMany(mappedBy = "imageFiles")
    private List<Blog> blog;
}
