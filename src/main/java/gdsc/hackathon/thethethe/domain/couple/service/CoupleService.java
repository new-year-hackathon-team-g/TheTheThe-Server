package gdsc.hackathon.thethethe.domain.couple.service;

import gdsc.hackathon.thethethe.domain.couple.dto.request.CoupleSaveRequest;
import gdsc.hackathon.thethethe.domain.couple.dto.response.CoupleFindResponse;
import gdsc.hackathon.thethethe.domain.couple.entity.Couple;
import gdsc.hackathon.thethethe.domain.couple.repository.CoupleRepository;
import gdsc.hackathon.thethethe.global.s3.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CoupleService {
    private final CoupleRepository coupleRepository;
    private final S3Service s3Service;

    public List<CoupleFindResponse> findCoupleRanking() {
        List<Couple> coupleList = coupleRepository.findByAllOrderByScoreDesc();
        List<CoupleFindResponse> coupleFindResponseList = new ArrayList<>();
        for (Couple couple : coupleList) {
            coupleFindResponseList.add(CoupleFindResponse.toDto(couple));
        }
        return coupleFindResponseList;
    }



    @Transactional
    public CoupleFindResponse findCouple(Long id) {
        Couple couple = coupleRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 id입니다."));
        return new CoupleFindResponse(couple.getCoupleName(),couple.getCoupleImageUrl(), couple.getIntroduction(), couple.getStartDate());
    }
    @Transactional
    public void saveCouple(CoupleSaveRequest coupleSaveRequest) {
        String coupleImageUrl = s3Service.saveImage(coupleSaveRequest.getCoupleImage());
        Couple couple = new Couple(coupleSaveRequest.getCoupleName(), coupleImageUrl, coupleSaveRequest.getIntroduction(),coupleSaveRequest.getStartDate());
        coupleRepository.save(couple);
    }
    @Transactional
    public void deleteCouple(Long id) {
        Couple couple = coupleRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 id입니다."));
        coupleRepository.delete(couple);
    }


}
