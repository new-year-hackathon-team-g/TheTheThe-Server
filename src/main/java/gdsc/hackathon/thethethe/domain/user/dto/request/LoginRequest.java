package gdsc.hackathon.thethethe.domain.user.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
