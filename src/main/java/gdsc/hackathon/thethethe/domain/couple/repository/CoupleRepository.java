package gdsc.hackathon.thethethe.domain.couple.repository;

import gdsc.hackathon.thethethe.domain.couple.entity.Couple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Long> {
    Optional<Couple> findById(Long id);
    List<Couple> findByAllOrderByScoreDesc();
}

