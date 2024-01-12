package gdsc.hackathon.thethethe.domain.couple.entity;

import gdsc.hackathon.thethethe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
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
    private CoupleStatus status;
    @Column(unique = true)
    private String secretCode;
    @OneToMany(mappedBy = "couple", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    @Builder
    public Couple(String coupleName, String coupleImageUrl, String introduction, String startDate, String secretCode) {
        this.coupleName = coupleName;
        this.coupleImageUrl = coupleImageUrl;
        this.introduction = introduction;
        this.startDate = startDate;
        this.status = CoupleStatus.WAITING;
        this.secretCode = secretCode;
    }

    public void updateStatus(CoupleStatus status) {
        this.status = status;
    }
}
