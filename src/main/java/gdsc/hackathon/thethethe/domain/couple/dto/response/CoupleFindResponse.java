package gdsc.hackathon.thethethe.domain.couple.dto.response;

import gdsc.hackathon.thethethe.domain.couple.entity.Couple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CoupleFindResponse {
    private String coupleName;
    private String coupleImageUrl;
    private String introduction;
    private String startDate;

    public static CoupleFindResponse toDto(Couple couple) {
        return new CoupleFindResponse(couple.getCoupleName(), couple.getCoupleImageUrl(), couple.getIntroduction(), couple.getStartDate()
        );
    }
}
