package gdsc.hackathon.thethethe.domain.user.entity;

import gdsc.hackathon.thethethe.domain.couple.entity.Couple;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String nickname;

    private String profileImageUrl;

    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Couple couple;

    @Builder
    public User(String email, String password, String nickname, String profileImageUrl, Integer score) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.score = score;
    }

    public void updateScore(Integer score){
        this.score = score;
    }
}
