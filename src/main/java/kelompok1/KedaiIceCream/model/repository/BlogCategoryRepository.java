package kelompok1.KedaiIceCream.model.repository;

import kelompok1.KedaiIceCream.model.entity.BlogCategory;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {

    
}
