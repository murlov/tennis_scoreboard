package murlov.tennis_scoreboard.model.domain.snapshot;

import java.util.UUID;

public record UnfinishedMatchSnapshot(UUID uuid,
                                      PlayerScoreSnapshot firstPlayerScoreSnapshot,
                                      PlayerScoreSnapshot secondPlayerScoreSnapshot,
                                      String winnerName) {
}
