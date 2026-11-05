package murlov.tennis_scoreboard.dto;

import murlov.tennis_scoreboard.model.domain.GamePoints;

public record PlayerScoreDto(String name,
                             GamePoints points,
                             int games,
                             int sets,
                             Integer tieBreakPoints) {
}
