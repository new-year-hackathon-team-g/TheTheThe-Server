package gdsc.hackathon.thethethe.domain.user.service;

import gdsc.hackathon.thethethe.domain.user.dto.request.LoginRequest;
import gdsc.hackathon.thethethe.domain.user.dto.request.SignupRequest;
import gdsc.hackathon.thethethe.domain.user.dto.response.PopResponse;
import gdsc.hackathon.thethethe.domain.user.dto.response.TokenResponse;
import gdsc.hackathon.thethethe.domain.user.entity.User;
import gdsc.hackathon.thethethe.domain.user.repository.UserRepository;
import gdsc.hackathon.thethethe.global.jwt.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .nickname(signupRequest.getNickname())
                .profileImageUrl(signupRequest.getProfileImageUrl())
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return tokenProvider.createToken(user.getEmail());
    }

    @Override
    @Transactional
    public PopResponse pop(String email, Integer score) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updateScore(user.getScore() + score);

        User couple = userRepository.findAllByCoupleIdAndEmailNot(user.getCouple().getId(), email)
                .stream()
                .max(Comparator.comparing(User::getScore))
                .orElseThrow(() -> new RuntimeException("User not found"));

        return PopResponse.builder()
                .myScore(user.getScore())
                .coupleScore(couple.getScore())
                .build();
    }
}
