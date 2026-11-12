package murlov.tennis_scoreboard.dto;

public record UnfinishedMatchResponseDto(PlayerScoreDto firstPlayerScore,
                                         PlayerScoreDto secondPlayerScore,
                                         String winnerName
) {
}
