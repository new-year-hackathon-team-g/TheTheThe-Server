package gdsc.hackathon.thethethe.domain.user.controller;

import gdsc.hackathon.thethethe.domain.user.dto.request.LoginRequest;
import gdsc.hackathon.thethethe.domain.user.dto.request.SignupRequest;
import gdsc.hackathon.thethethe.domain.user.dto.response.TokenResponse;
import gdsc.hackathon.thethethe.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest signupRequest) {
        return userService.signup(signupRequest);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

}
