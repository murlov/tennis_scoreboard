package murlov.tennis_scoreboard.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long player1;
    private Long player2;
    private Long winner;
    private int id;

    public Match(Long player1, Long player2, Long winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }
}
