package kelompok1.KedaiIceCream.model.repository;

import kelompok1.KedaiIceCream.model.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
}