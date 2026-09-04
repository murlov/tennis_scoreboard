package murlov.tennis_scoreboard.service;

import murlov.tennis_scoreboard.dao.MatchDao;
import murlov.tennis_scoreboard.dao.PlayerDao;
import murlov.tennis_scoreboard.dto.MatchRequestDto;
import murlov.tennis_scoreboard.exception.NotFoundException;
import murlov.tennis_scoreboard.model.Match;
import murlov.tennis_scoreboard.model.Player;

public class MatchService {

    private final MatchDao matchDao;
    private final PlayerDao playerDao;

    public MatchService(MatchDao matchDao, PlayerDao playerDao) {
        this.matchDao = matchDao;
        this.playerDao = playerDao;
    }

    public Match save(MatchRequestDto matchRequestDto) {
        Player firstPlayer = playerDao
                .getByName(matchRequestDto.firstPlayerName())
                .orElseThrow(() -> new NotFoundException(
                        "Player not found: " + matchRequestDto
                                .firstPlayerName()
                ));

        Player secondPlayer = playerDao
                .getByName(matchRequestDto.secondPlayerName())
                .orElseThrow(() -> new NotFoundException(
                        "Player not found: " + matchRequestDto
                                .secondPlayerName()
                ));

        Match match = createMatch(firstPlayer.getId(), secondPlayer.getId());

        return matchDao.save(match);
    }

    private Match createMatch(Long firstPlayerId, Long secondPlayerId) {
        return new Match(
                null,
                firstPlayerId,
                secondPlayerId,
                null
        );
    }
}
