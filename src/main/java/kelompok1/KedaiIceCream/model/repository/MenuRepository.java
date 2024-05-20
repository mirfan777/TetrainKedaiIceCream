package kelompok1.KedaiIceCream.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kelompok1.KedaiIceCream.model.entity.Menu;
import kelompok1.KedaiIceCream.model.entity.MenuVariant;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    // Additional custom methods, if needed
}