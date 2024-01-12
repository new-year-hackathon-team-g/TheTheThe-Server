package gdsc.hackathon.thethethe.domain.couple.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoupleSaveRequest {
    private String coupleName;
    private String coupleImageUrl;
    private String introduction;
    private String startDate;
}
