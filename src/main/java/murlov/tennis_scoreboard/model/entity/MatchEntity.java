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
    private PlayerEntity playerEntity1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_player_id", nullable = false)
    private PlayerEntity playerEntity2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private PlayerEntity winner;

    public MatchEntity(PlayerEntity playerEntity1, PlayerEntity playerEntity2, PlayerEntity winner) {
        this.playerEntity1 = playerEntity1;
        this.playerEntity2 = playerEntity2;
        this.winner = winner;
    }
}
