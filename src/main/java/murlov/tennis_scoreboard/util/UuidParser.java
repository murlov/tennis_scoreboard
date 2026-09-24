package murlov.tennis_scoreboard.util;

import jakarta.servlet.http.HttpServletRequest;
import murlov.tennis_scoreboard.exception.ValidationException;
import murlov.tennis_scoreboard.util.validator.PointValidator;

import java.util.UUID;

public final class UuidParser {

    private static final int MATCH_ID_INDEX = 1;

    private UuidParser() {}

    public static UUID parse(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();

        String[] parts = pathInfo.split("/");

        PointValidator.validateParts(parts);

        String matchId = parts[MATCH_ID_INDEX];

        try {
            return UUID.fromString(matchId);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(
                    "Match ID must be in UUID format"
            );
        }
    }
}
