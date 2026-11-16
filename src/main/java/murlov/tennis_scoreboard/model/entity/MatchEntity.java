package murlov.tennis_scoreboard.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matches")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_player_id", nullable = false)
    private PlayerEntity firstPlayerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_player_id", nullable = false)
    private PlayerEntity secondPlayerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private PlayerEntity winnerEntity;

    public MatchEntity(PlayerEntity firstPlayerEntity, PlayerEntity secondPlayerEntity, PlayerEntity winnerEntity) {
        this.firstPlayerEntity = firstPlayerEntity;
        this.secondPlayerEntity = secondPlayerEntity;
        this.winnerEntity = winnerEntity;
    }
}
