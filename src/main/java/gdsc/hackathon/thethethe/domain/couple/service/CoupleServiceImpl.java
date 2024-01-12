package gdsc.hackathon.thethethe.domain.couple.service;

import gdsc.hackathon.thethethe.domain.couple.dto.request.CoupleCreateRequest;
import gdsc.hackathon.thethethe.domain.couple.dto.response.CoupleResponse;
import gdsc.hackathon.thethethe.domain.couple.entity.Couple;
import gdsc.hackathon.thethethe.domain.couple.entity.CoupleStatus;
import gdsc.hackathon.thethethe.domain.couple.repository.CoupleRepository;
import gdsc.hackathon.thethethe.domain.user.dto.response.UserResponse;
import gdsc.hackathon.thethethe.domain.user.entity.User;
import gdsc.hackathon.thethethe.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoupleServiceImpl implements CoupleService {

    private final CoupleRepository coupleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CoupleResponse findCouple(String email) {
        User user = userRepository.findByEmailWithCouple(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저 이메일입니다."));

        Couple couple = user.getCouple();
        List<User> users = couple.getUsers();

        Integer coupleTotalScore = userRepository.findSumOfMaxScoresInCouple(couple.getId())
                .orElse(0);

        return CoupleResponse.builder()
                .id(couple.getId())
                .coupleName(couple.getCoupleName())
                .coupleImageUrl(couple.getCoupleImageUrl())
                .introduction(couple.getIntroduction())
                .startDate(couple.getStartDate())
                .status(couple.getStatus().name())
                .users(users.stream()
                        .map(user1 -> UserResponse.builder()
                                .id(user1.getId())
                                .nickname(user1.getNickname())
                                .email(user1.getEmail())
                                .profileImageUrl(user1.getProfileImageUrl())
                                .score(user1.getScore())
                                .build())
                        .toList())
                .coupleTotalScore(coupleTotalScore)
                .build();
    }


    @Override
    @Transactional
    public String createCouple(String email, CoupleCreateRequest coupleCreateRequest) {
        Couple couple = Couple.builder()
                .coupleName(coupleCreateRequest.getCoupleName())
                .coupleImageUrl(coupleCreateRequest.getCoupleImageUrl())
                .introduction(coupleCreateRequest.getIntroduction())
                .startDate(coupleCreateRequest.getStartDate())
                .secretCode(generateSecretCode())
                .build();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저 id입니다."));

        user.setCouple(couple);

        coupleRepository.save(couple);
        return couple.getSecretCode();
    }

    private String generateSecretCode() {
        UUID uuid = UUID.randomUUID();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(getMostSignificantBits(uuid));
    }

    private byte[] getMostSignificantBits(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        byte[] bytes = new byte[8];
        for (int i = 7; i >= 0; i--) {
            bytes[i] = (byte) (msb & 0xFF);
            msb >>= 8;
        }
        return bytes;
    }

    @Override
    @Transactional
    public String acceptCouple(String email, String secretCode) {
        Couple couple = coupleRepository.findBySecretCode(secretCode)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 커플 코드입니다."));

        int updatedCoupleRows = coupleRepository.updateCoupleStatusBySecretCode(CoupleStatus.ACCEPTED, secretCode);
        if (updatedCoupleRows == 0) {
            throw new RuntimeException("커플 상태를 변경할 수 없습니다.");
        }

        int updatedUserRows = userRepository.updateUserCoupleByEmail(couple, email);
        if (updatedUserRows == 0) {
            throw new RuntimeException("사용자의 커플을 설정할 수 없습니다.");
        }

        return "success";
    }

    @Override
    @Transactional
    public List<CoupleResponse> findCoupleRanking() {
        List<Couple> couples = coupleRepository.findAll();

        return couples.stream()
                .sorted(Comparator.comparingInt(this::getCoupleTotalScore).reversed())
                .map(couple -> CoupleResponse.builder()
                        .id(couple.getId())
                        .coupleName(couple.getCoupleName())
                        .coupleImageUrl(couple.getCoupleImageUrl())
                        .introduction(couple.getIntroduction())
                        .startDate(couple.getStartDate())
                        .status(couple.getStatus().name())
                        .coupleTotalScore(getCoupleTotalScore(couple))
                        .users(couple.getUsers().stream()
                                .map(user -> UserResponse.builder()
                                        .id(user.getId())
                                        .nickname(user.getNickname())
                                        .email(user.getEmail())
                                        .profileImageUrl(user.getProfileImageUrl())
                                        .score(user.getScore())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }

    private int getCoupleTotalScore(Couple couple) {
        return couple.getUsers().stream()
                .map(User::getScore)
                .mapToInt(Integer::intValue)
                .sum();
    }

}
