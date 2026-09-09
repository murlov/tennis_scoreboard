package murlov.tennis_scoreboard.dto;

import murlov.tennis_scoreboard.model.PlayerScore;

public record PointResponseDto(
        PlayerScore firstPlayerScore,
        PlayerScore secondPlayerScore,
        String winnerName
) {
}
