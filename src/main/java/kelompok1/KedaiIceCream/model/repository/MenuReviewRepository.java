package kelompok1.KedaiIceCream.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kelompok1.KedaiIceCream.model.entity.MenuReview;

public interface MenuReviewRepository extends JpaRepository<MenuReview, Long> {
   @Query("SELECT mr FROM MenuReview mr JOIN FETCH mr.menu m WHERE m.id = :menuId")
    Page<MenuReview> findByMenuId(@Param("menuId") Long menuId, Pageable pageable);
}
