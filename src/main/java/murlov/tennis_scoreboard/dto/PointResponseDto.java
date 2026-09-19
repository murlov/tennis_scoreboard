package murlov.tennis_scoreboard.dto;

public record PointResponseDto(PlayerScoreDto firstPlayerScore,
                               PlayerScoreDto secondPlayerScore,
                               String winnerName
) {
}
