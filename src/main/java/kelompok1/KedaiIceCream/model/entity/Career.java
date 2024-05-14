package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "careers")
@Data
@NoArgsConstructor 
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(name = "title" , nullable = false)
    private String title;

    @NotBlank(message = "Description is required")
    @Column(name = "description", columnDefinition = "TEXT" , nullable = false)
    private String description;
    
    @NotNull(message = "Start date is required")
    @Column(name = "start_at" , nullable = false)
    private LocalDate startAt;

    @NotNull(message = "Close date is required")
    @Column(name = "close_at" , nullable = false)
    private LocalDate closeAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}