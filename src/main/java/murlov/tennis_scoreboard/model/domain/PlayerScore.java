package murlov.tennis_scoreboard.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlayerScore {
    private final int id;
    private final String name;
    private GamePoints points;
    private int games;
    private int sets;
    private Integer tieBreakPoints;

    public PlayerScore(int id, String name) {
        this.id = id;
        points = GamePoints.ZERO;
        this.name = name;
    }
}
