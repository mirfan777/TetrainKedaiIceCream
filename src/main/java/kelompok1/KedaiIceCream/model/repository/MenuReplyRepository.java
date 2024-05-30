package kelompok1.KedaiIceCream.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kelompok1.KedaiIceCream.model.entity.MenuReply;

public interface MenuReplyRepository extends JpaRepository<MenuReply, Long> {
    // Additional custom methods, if needed
}
