package kelompok1.KedaiIceCream.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "main_data")
@Data
public class MainData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contact_phone;
    private String gmap_link;
    private String address;
    private String instagram_link;
    private String twitter_link;
    private String facebook_link;
    private String youtube_link;
}