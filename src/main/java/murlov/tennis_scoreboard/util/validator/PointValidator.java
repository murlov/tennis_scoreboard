package murlov.tennis_scoreboard.util.validator;

import murlov.tennis_scoreboard.dto.PointRequestDto;
import murlov.tennis_scoreboard.exception.ValidationException;

public final class PointValidator {

    private PointValidator() {}

    public static void validateParts(String[] parts) {

        if (parts.length != 3 || parts[2].isBlank()
                || !parts[2].equals("point")) {
            throw new ValidationException(
                    "Path must have the form /matches/{{id}}/point"
            );
        }

        if (parts[1].isBlank()) {
            throw new ValidationException(
                    "Path must contain the match ID and have the form /matches/{{id}}/point"
            );
        }
    }

    public static void validatePointRequestDto(PointRequestDto pointRequestDto) {
        if (pointRequestDto.name() == null
                || pointRequestDto.name().isBlank()) {
            throw new ValidationException(
                    "Player name must not be blank"
            );
        }
    }
}
