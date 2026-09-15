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
                new PlayerScore(firstPlayer.getName()),
                new PlayerScore(secondPlayer.getName())
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
        Player firstPlayer = playerDao.getByName(firstPlayerName)
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Player not found: " + firstPlayerName
                        )
                );

        String secondPlayerName = unfinishedMatch
                .getSecondPlayerScore()
                .getName();
        Player secondPlayer = playerDao.getByName(secondPlayerName)
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Player not found: " + secondPlayerName
                        )
                );

        Long winner;
        if (unfinishedMatch.getWinnerName().equals(firstPlayerName)) {
            winner = firstPlayer.getId();
        } else {
            winner = secondPlayer.getId();
        }

        Match match = new Match(
                firstPlayer.getId(),
                secondPlayer.getId(),
                winner
        );

        matchDao.save(match);
        unfinishedMatchesStorage.remove(matchUuid);
    }
}
