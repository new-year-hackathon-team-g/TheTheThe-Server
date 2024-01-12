package gdsc.hackathon.thethethe.domain.user.controller;

import gdsc.hackathon.thethethe.domain.couple.entity.Couple;
import gdsc.hackathon.thethethe.domain.user.dto.request.LoginRequest;
import gdsc.hackathon.thethethe.domain.user.dto.request.SignupRequest;
import gdsc.hackathon.thethethe.domain.user.dto.response.TokenResponse;
import gdsc.hackathon.thethethe.domain.user.service.UserService;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Couple couple;
}
