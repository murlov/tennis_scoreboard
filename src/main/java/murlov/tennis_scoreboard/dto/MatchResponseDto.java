package murlov.tennis_scoreboard.dto;

public record MatchResponseDto(PlayerScoreDto firstPlayerScore,
                               PlayerScoreDto secondPlayerScore,
                               String winnerName
) {
}
