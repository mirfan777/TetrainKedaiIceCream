package kelompok1.KedaiIceCream.model.repository;

import kelompok1.KedaiIceCream.model.entity.Career;
import kelompok1.KedaiIceCream.model.model.CareerDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {

    @Query("SELECT c FROM Career c " +
            "WHERE (:searchTerm IS NULL OR c.title LIKE %:searchTerm% OR c.description LIKE %:searchTerm%) " +
            "AND (:openFilter IS NULL OR c.startAt >= :openFilter) " +
            "AND (:closeFilter IS NULL OR c.closeAt <= :closeFilter) " +
            "AND (:createdFilter IS NULL OR c.createdAt >= :createdFilter)")
    Page<Career> findAllWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("openFilter") LocalDate openFilter,
            @Param("closeFilter") LocalDate closeFilter,
            @Param("createdFilter") LocalDateTime createdFilter,
            Pageable pageable
    );

Career save(@Valid CareerDto career);
}
