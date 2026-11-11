package murlov.tennis_scoreboard.util.validator;

import murlov.tennis_scoreboard.dto.NewMatchRequestDto;
import murlov.tennis_scoreboard.exception.ValidationException;

public final class MatchValidator {

    private MatchValidator() {}

    public static void validate(NewMatchRequestDto newMatchRequestDto) {

        if (newMatchRequestDto.firstPlayerName()
                .equals(newMatchRequestDto.secondPlayerName())) {
            throw new ValidationException(
                    "Player names must be different"
            );
        }
    }
}
