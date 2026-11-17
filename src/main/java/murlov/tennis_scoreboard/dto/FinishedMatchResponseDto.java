package murlov.tennis_scoreboard.dto;

public record FinishedMatchResponseDto(String firstPlayerName,
                                       String secondPlayerName,
                                       String winnerName) {
}
