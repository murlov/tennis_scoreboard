package murlov.tennis_scoreboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewMatchRequestDto(
        @NotBlank
        @Size(max = 50)
        String firstPlayerName,

        @NotBlank
        @Size(max = 50)
        String secondPlayerName
){
        public NewMatchRequestDto {
                firstPlayerName = firstPlayerName == null ? null : firstPlayerName.strip();
                secondPlayerName = secondPlayerName == null ? null : secondPlayerName.strip();
        }
}
