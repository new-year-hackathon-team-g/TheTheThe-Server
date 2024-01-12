package gdsc.hackathon.thethethe.domain.user.repository;

import gdsc.hackathon.thethethe.domain.couple.entity.Couple;
import gdsc.hackathon.thethethe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailWithCouple(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.couple = :couple WHERE u.email = :email")
    int updateUserCoupleByEmail(@Param("couple") Couple couple, @Param("email") String email);

    @Query("SELECT SUM(u.score) FROM User u WHERE u.couple.id = :coupleId")
    Optional<Integer> findSumOfMaxScoresInCouple(@Param("coupleId") Long coupleId);
}
