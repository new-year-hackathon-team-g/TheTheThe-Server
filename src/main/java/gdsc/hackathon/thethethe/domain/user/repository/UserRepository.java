package gdsc.hackathon.thethethe.domain.user.repository;

import gdsc.hackathon.thethethe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findAllByCoupleId(Long coupleId);
    List<User> findAllByCoupleIdAndEmailNot(Long coupleId, String email);
}
