package gdsc.hackathon.thethethe.domain.couple.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CoupleSaveRequest {
    private String coupleName;
    private MultipartFile coupleImage;
    private String introduction;
    private String startDate;

}
