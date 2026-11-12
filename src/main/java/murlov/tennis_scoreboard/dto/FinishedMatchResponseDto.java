package murlov.tennis_scoreboard.dto;

public record FinishedMatchResponseDto(String firsPlayerName,
                                       String secondPlayerName,
                                       String winnerName) {
}
