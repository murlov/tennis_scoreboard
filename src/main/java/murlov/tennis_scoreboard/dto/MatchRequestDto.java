package murlov.tennis_scoreboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MatchRequestDto (
        @NotBlank
        @Size(max = 50)
        String firstPlayerName,

        @NotBlank
        @Size(max = 50)
        String secondPlayerName
){
}
