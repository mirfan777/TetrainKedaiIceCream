package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private Integer price;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "promo")
    private String promo;

    @NotBlank(message = "title is required")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Description is required")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuReview> menuReviews;

    public int getAverageRating() {
        if (menuReviews == null || menuReviews.isEmpty()) {
            return 0; // Return 0 if there are no comments
        }

        List<Integer> ratings = menuReviews.stream()
                .filter(comment -> comment.getRating() != null)
                .map(comment -> comment.getRating())
                .collect(Collectors.toList());

        if (ratings.isEmpty()) {
            return 0; // Return 0 if there are no ratings
        }

        double averageRating = ratings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        return (int) Math.round(averageRating);
    }
}
