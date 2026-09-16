package murlov.tennis_scoreboard.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerScore {
    private final String name;
    private GamePoints points;
    private int games;
    private int sets;
    private Integer tieBreakPoints;

    public PlayerScore(String name) {
        points = GamePoints.ZERO;
        this.name = name;
    }
}
