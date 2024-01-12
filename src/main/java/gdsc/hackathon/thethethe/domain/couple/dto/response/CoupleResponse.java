package gdsc.hackathon.thethethe.domain.couple.dto.response;

import gdsc.hackathon.thethethe.domain.user.dto.response.UserResponse;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Formula;

import java.util.List;

@Data
@Builder
public class CoupleResponse {
    private Long id;
    private String coupleName;
    private String coupleImageUrl;
    private String introduction;
    private String startDate;
    private String status;
    @Formula("(SELECT COALESCE(SUM(u.score), 0) FROM User u WHERE u.couple_id = couple_id)")
    private int coupleTotalScore;
    List<UserResponse> users;
}
