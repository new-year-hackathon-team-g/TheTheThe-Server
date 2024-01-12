package gdsc.hackathon.thethethe.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PopResponse {
    Integer myScore;
    Integer coupleScore;
}
