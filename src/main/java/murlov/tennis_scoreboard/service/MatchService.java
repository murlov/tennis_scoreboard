package murlov.tennis_scoreboard.service;

import murlov.tennis_scoreboard.dao.PlayerDao;
import murlov.tennis_scoreboard.dto.MatchRequestDto;
import murlov.tennis_scoreboard.model.Player;
import murlov.tennis_scoreboard.model.PlayerScore;
import murlov.tennis_scoreboard.model.UnfinishedMatch;
import murlov.tennis_scoreboard.storage.UnfinishedMatchesStorage;

import java.util.UUID;

public class MatchService {

    private final PlayerDao playerDao;
    private final UnfinishedMatchesStorage unfinishedMatchesStorage;

    public MatchService(PlayerDao playerDao, UnfinishedMatchesStorage unfinishedMatchesStorage) {
        this.playerDao = playerDao;
        this.unfinishedMatchesStorage = unfinishedMatchesStorage;
    }

    public UUID createMatch(MatchRequestDto matchRequestDto) {
        Player firstPlayer = playerDao
                .getByName(matchRequestDto.firstPlayerName())
                .orElseGet(() -> playerDao.save(
                        matchRequestDto.firstPlayerName()
                ));

        Player secondPlayer = playerDao
                .getByName(matchRequestDto.secondPlayerName())
                .orElseGet(() -> playerDao.save(
                        matchRequestDto.secondPlayerName()
                ));

        UnfinishedMatch unfinishedMatch = new UnfinishedMatch(
                new PlayerScore(firstPlayer.getName()),
                new PlayerScore(secondPlayer.getName())
        );

        unfinishedMatchesStorage.save(unfinishedMatch);

        return unfinishedMatch.getUuid();
    }
}
