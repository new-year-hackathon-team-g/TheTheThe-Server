package gdsc.hackathon.thethethe.domain.couple.dto.request;

import lombok.Data;

@Data
public class CoupleCreateRequest {
    private String coupleName;
    private String coupleImageUrl;
    private String introduction;
    private String startDate;
}
