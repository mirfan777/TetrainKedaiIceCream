package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "menus")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private MenuCategory category;

    @ManyToOne
    @JoinColumn(name = "image_files", referencedColumnName = "id" , nullable = true)
    private ImageFile imageFiles;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "menu")
    private List<MenuComment> menuComments;

    @OneToMany(mappedBy = "menu")
    private List<MenuVariant> menuVariants;

    @OneToMany(mappedBy = "menu")
    private List<MenuAdding> menuAddings;
}
