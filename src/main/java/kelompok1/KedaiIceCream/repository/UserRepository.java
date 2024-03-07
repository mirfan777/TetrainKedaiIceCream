package kelompok1.KedaiIceCream.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kelompok1.KedaiIceCream.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { }
