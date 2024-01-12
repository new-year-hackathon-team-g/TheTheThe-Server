package gdsc.hackathon.thethethe.domain.couple.service;

import gdsc.hackathon.thethethe.domain.couple.dto.request.CoupleCreateRequest;
import gdsc.hackathon.thethethe.domain.couple.dto.response.CoupleResponse;

import java.util.List;

public interface CoupleService {
    CoupleResponse findCouple(String email);
    String createCouple(String email, CoupleCreateRequest coupleCreateRequest);
    String acceptCouple(String email, String secretCode);
    List<CoupleResponse> findCoupleRanking();
}
