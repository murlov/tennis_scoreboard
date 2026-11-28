package murlov.tennis_scoreboard.model.domain.snapshot;

public record UnfinishedMatchSnapshot(PlayerScoreSnapshot firstPlayerScore,
                                      PlayerScoreSnapshot secondPlayerScore,
                                      String winnerName) {
}
