package gdsc.hackathon.thethethe.domain.video.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoResponse {
    private String videoId;
    private String title;
    private String thumbnailUrl;
}
