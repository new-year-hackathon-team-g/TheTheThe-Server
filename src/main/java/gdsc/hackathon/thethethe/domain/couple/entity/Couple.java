package gdsc.hackathon.thethethe.domain.couple.entity;

import gdsc.hackathon.thethethe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_id")
    private Long id;
    @Column
    private String coupleName;
    @Column
    private String coupleImageUrl;
    @Column
    private String introduction;
    @Column
    private String startDate;
    @Column
    private Integer score;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<User> userList = new ArrayList<>();

    public void updateScore(Integer score){
        this.score = score;
    }
    public Couple(String coupleName, String coupleImageUrl, String introduction, String startDate) {
        this.coupleName = coupleName;
        this.coupleImageUrl = coupleImageUrl;
        this.introduction = introduction;
        this.startDate = startDate;
        this.score = 0;
    }
}
