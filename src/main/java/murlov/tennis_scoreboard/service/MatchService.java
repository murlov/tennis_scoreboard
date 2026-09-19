package murlov.tennis_scoreboard.service;

import murlov.tennis_scoreboard.dao.MatchDao;
import murlov.tennis_scoreboard.dao.PlayerDao;
import murlov.tennis_scoreboard.dto.MatchRequestDto;
import murlov.tennis_scoreboard.dto.PointRequestDto;
import murlov.tennis_scoreboard.model.Match;
import murlov.tennis_scoreboard.model.Player;
import murlov.tennis_scoreboard.model.PlayerScore;
import murlov.tennis_scoreboard.model.UnfinishedMatch;
import murlov.tennis_scoreboard.storage.UnfinishedMatchesStorage;

import java.util.UUID;

public class MatchService {

    private final PlayerDao playerDao;
    private final MatchDao matchDao;
    private final UnfinishedMatchesStorage unfinishedMatchesStorage;

    public MatchService(PlayerDao playerDao, MatchDao matchDao, UnfinishedMatchesStorage unfinishedMatchesStorage) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
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
                new PlayerScore(firstPlayer.getId(), firstPlayer.getName()),
                new PlayerScore(secondPlayer.getId(), secondPlayer.getName())
        );

        unfinishedMatchesStorage.save(unfinishedMatch);

        return unfinishedMatch.getUuid();
    }

    public UnfinishedMatch addPoint(UUID matchUuid, PointRequestDto pointRequestDto) {
        UnfinishedMatch unfinishedMatch = unfinishedMatchesStorage.get(matchUuid);
        unfinishedMatch.addPoint(pointRequestDto.name());

        if (unfinishedMatch.getWinnerName() != null) {
            saveMatch(unfinishedMatch, matchUuid);
        }

        return unfinishedMatch;
    }

    private void saveMatch(UnfinishedMatch unfinishedMatch, UUID matchUuid) {
        String firstPlayerName = unfinishedMatch
                .getFirstPlayerScore()
                .getName();
        Long firstPlayerId = unfinishedMatch
                .getFirstPlayerScore()
                .getId();
        Long secondPlayerId = unfinishedMatch
                .getSecondPlayerScore()
                .getId();
        Long winner;

        if (unfinishedMatch.getWinnerName().equals(firstPlayerName)) {
            winner = firstPlayerId;
        } else {
            winner = secondPlayerId;
        }

        Match match = new Match(
                firstPlayerId,
                secondPlayerId,
                winner
        );

        matchDao.save(match);
        unfinishedMatchesStorage.remove(matchUuid);
    }
}
