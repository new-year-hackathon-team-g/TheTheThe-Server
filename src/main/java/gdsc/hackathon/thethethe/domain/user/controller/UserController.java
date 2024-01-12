package gdsc.hackathon.thethethe.domain.user.controller;

import gdsc.hackathon.thethethe.domain.user.dto.request.LoginRequest;
import gdsc.hackathon.thethethe.domain.user.dto.request.SignupRequest;
import gdsc.hackathon.thethethe.domain.user.dto.response.PopResponse;
import gdsc.hackathon.thethethe.domain.user.dto.response.TokenResponse;
import gdsc.hackathon.thethethe.domain.user.service.UserService;;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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

    @PostMapping("/pop/{score}")
    public PopResponse pop(Principal principal, @PathVariable Integer score) {
        return userService.pop(principal.getName(), score);
    }
}
