package kelompok1.KedaiIceCream.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kelompok1.KedaiIceCream.model.entity.MenuVariant;

public interface MenuVariantRepository extends JpaRepository<MenuVariant, Long> {
    // Additional custom methods, if needed
}
