package gdsc.hackathon.thethethe.domain.couple.repository;

import gdsc.hackathon.thethethe.domain.couple.entity.Couple;
import gdsc.hackathon.thethethe.domain.couple.entity.CoupleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Long> {
    Optional<Couple> findBySecretCode(String secretCode);

    @Modifying
    @Query("UPDATE Couple c SET c.status = :status WHERE c.secretCode = :secretCode")
    int updateCoupleStatusBySecretCode(@Param("status") CoupleStatus status, @Param("secretCode") String secretCode);
}

