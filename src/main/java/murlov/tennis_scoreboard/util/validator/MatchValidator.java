package murlov.tennis_scoreboard.util.validator;

import murlov.tennis_scoreboard.dto.MatchRequestDto;
import murlov.tennis_scoreboard.exception.ValidationException;

public final class MatchValidator {

    private MatchValidator() {}

    public static void validate(MatchRequestDto matchRequestDto) {
        if (matchRequestDto.firstPlayerName().length() > 100
                || matchRequestDto.secondPlayerName().length() > 100) {
            throw new ValidationException(
                    "Player name is too long. Maximum length is 100 characters."
            );
        }

        if (matchRequestDto.firstPlayerName().isBlank()
                || matchRequestDto.secondPlayerName().isBlank()) {
            throw new ValidationException(
                    "Player name must not be blank"
            );
        }

        if (matchRequestDto.firstPlayerName()
                .equals(matchRequestDto.secondPlayerName())) {
            throw new ValidationException(
                    "Player names must be different"
            );
        }
    }
}
