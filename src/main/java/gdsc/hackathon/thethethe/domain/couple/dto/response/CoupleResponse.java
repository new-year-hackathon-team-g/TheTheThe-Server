package gdsc.hackathon.thethethe.domain.couple.dto.response;

import gdsc.hackathon.thethethe.domain.user.dto.response.UserResponse;
import lombok.Builder;
import lombok.Data;

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
    private int coupleTotalScore;
    List<UserResponse> users;
}
