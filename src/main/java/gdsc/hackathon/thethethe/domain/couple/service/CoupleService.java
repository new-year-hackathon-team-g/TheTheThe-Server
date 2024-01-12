package gdsc.hackathon.thethethe.domain.couple.service;

import gdsc.hackathon.thethethe.domain.couple.dto.request.CoupleSaveRequest;
import gdsc.hackathon.thethethe.domain.couple.dto.request.PopUpdateRequest;
import gdsc.hackathon.thethethe.domain.couple.dto.response.CoupleFindResponse;
import gdsc.hackathon.thethethe.domain.couple.dto.response.PopFindResponse;
import gdsc.hackathon.thethethe.domain.couple.entity.Couple;
import gdsc.hackathon.thethethe.domain.couple.repository.CoupleRepository;
import gdsc.hackathon.thethethe.domain.user.entity.User;
import gdsc.hackathon.thethethe.domain.user.repository.UserRepository;
import gdsc.hackathon.thethethe.global.s3.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CoupleService {
    private final CoupleRepository coupleRepository;
    private final UserRepository userRepository;
    //private final S3Service s3Service;

    public List<CoupleFindResponse> findCoupleRanking() {
        List<Couple> coupleList = coupleRepository.findAllByOrderByScoreDesc();
        List<CoupleFindResponse> coupleFindResponseList = new ArrayList<>();
        for (Couple couple : coupleList) {
            coupleFindResponseList.add(CoupleFindResponse.toDto(couple));
        }
        return coupleFindResponseList;
    }

    @Transactional
    public CoupleFindResponse findCouple(Long id) {
        Couple couple = coupleRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 커플 id입니다."));
        return new CoupleFindResponse(couple.getCoupleName(),couple.getCoupleImageUrl(), couple.getIntroduction(), couple.getStartDate(), couple.getScore());
    }
    @Transactional
    public void saveCouple(CoupleSaveRequest coupleSaveRequest) {
        Couple couple = new Couple(coupleSaveRequest.getCoupleName(), coupleSaveRequest.getCoupleImageUrl(), coupleSaveRequest.getIntroduction(),coupleSaveRequest.getStartDate());
        coupleRepository.save(couple);
    }
    @Transactional
    public void deleteCouple(Long id) {
        Couple couple = coupleRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 커플 id입니다."));
        coupleRepository.delete(couple);
    }
    @Transactional
    public PopFindResponse findPop(Long id){
        User user  = userRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 유저 id입니다."));
        List<User> userList =  user.getCouple().getUserList();
        List<PopFindResponse> popFindResponseList = new ArrayList<>();
        User otherUser = null;
        for (User tempUser : userList) {
            otherUser = tempUser;
            if(!tempUser.getId().equals(user.getId())){
               break;
           }
        }
        PopFindResponse popFindResponse = PopFindResponse.toDto(user, otherUser);
        return popFindResponse;
    }

    @Transactional
    public void updatePop(Long id, PopUpdateRequest popUpdateRequest){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 유저 id입니다."));
        //개인 스코어 업데이트
        user.updateScore(user.getScore() + popUpdateRequest.getScore());
        //커플 스코어 업데이트
        user.getCouple().updateScore(user.getCouple().getScore() + popUpdateRequest.getScore());
    }
}
