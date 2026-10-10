package murlov.tennis_scoreboard.util.validator;

import murlov.tennis_scoreboard.dto.MatchRequestDto;
import murlov.tennis_scoreboard.exception.ValidationException;

public final class MatchValidator {

    private MatchValidator() {}

    public static void validate(MatchRequestDto matchRequestDto) {

        if (matchRequestDto.firstPlayerName()
                .equals(matchRequestDto.secondPlayerName())) {
            throw new ValidationException(
                    "Player names must be different"
            );
        }
    }
}
