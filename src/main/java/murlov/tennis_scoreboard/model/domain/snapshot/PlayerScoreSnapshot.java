package murlov.tennis_scoreboard.model.domain.snapshot;

import murlov.tennis_scoreboard.model.domain.GamePoints;

public record PlayerScoreSnapshot(String name,
                                  GamePoints points,
                                  int games,
                                  int sets,
                                  Integer tieBreakPoints) {
}
