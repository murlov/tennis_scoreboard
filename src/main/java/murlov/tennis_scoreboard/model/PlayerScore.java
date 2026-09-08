package murlov.tennis_scoreboard.model;

import lombok.Data;

@Data
public class PlayerScore {
    private final String name;
    private Integer points;
    private int games;
    private int sets;
    private Integer tieBreakPoints;

    public PlayerScore(String name) {
        this.name = name;
    }
}
