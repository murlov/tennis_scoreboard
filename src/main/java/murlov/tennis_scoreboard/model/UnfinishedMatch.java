package murlov.tennis_scoreboard.model;

import lombok.Data;

import java.util.UUID;

@Data
public class UnfinishedMatch {
    private final UUID uuid;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
    private String winnerName;

    public UnfinishedMatch(PlayerScore firstPlayerScore,
                           PlayerScore secondPlayerScore) {
        uuid = UUID.randomUUID();
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }
}
