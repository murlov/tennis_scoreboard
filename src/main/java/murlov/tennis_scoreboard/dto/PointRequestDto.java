package murlov.tennis_scoreboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PointRequestDto(
        @NotBlank
        @Size(max = 50)
        String name
) {
}
