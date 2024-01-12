package gdsc.hackathon.thethethe.domain.user.service;

import gdsc.hackathon.thethethe.domain.user.dto.request.LoginRequest;
import gdsc.hackathon.thethethe.domain.user.dto.request.SignupRequest;
import gdsc.hackathon.thethethe.domain.user.dto.response.TokenResponse;

public interface UserService {
    String signup(SignupRequest signupRequest);
    TokenResponse login(LoginRequest loginRequest);
}
