package murlov.tennis_scoreboard.service;

import murlov.tennis_scoreboard.repository.MatchRepository;
import murlov.tennis_scoreboard.repository.PlayerRepository;
import murlov.tennis_scoreboard.dto.MatchRequestDto;
import murlov.tennis_scoreboard.dto.PointRequestDto;
import murlov.tennis_scoreboard.exception.NotFoundException;
import murlov.tennis_scoreboard.model.Player;
import murlov.tennis_scoreboard.model.PlayerScore;
import murlov.tennis_scoreboard.model.UnfinishedMatch;
import murlov.tennis_scoreboard.storage.UnfinishedMatchesStorage;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MatchService {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final UnfinishedMatchesStorage unfinishedMatchesStorage;

    public MatchService(PlayerRepository playerRepository, MatchRepository matchRepository, UnfinishedMatchesStorage unfinishedMatchesStorage) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.unfinishedMatchesStorage = unfinishedMatchesStorage;
    }

    public UUID createMatch(MatchRequestDto matchRequestDto) {
        Player firstPlayer = playerRepository
                .getByName(matchRequestDto.firstPlayerName())
                .orElseGet(() -> playerRepository.save(
                        matchRequestDto.firstPlayerName()
                ));

        Player secondPlayer = playerRepository
                .getByName(matchRequestDto.secondPlayerName())
                .orElseGet(() -> playerRepository.save(
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
        UnfinishedMatch unfinishedMatch = getMatch(matchUuid);
        unfinishedMatch.addPoint(pointRequestDto.name());

        if (unfinishedMatch.getWinnerName() != null) {
            saveMatch(unfinishedMatch, matchUuid);
        }

        return unfinishedMatch;
    }

    public UnfinishedMatch getMatch(UUID matchUuid) {
        return unfinishedMatchesStorage.get(matchUuid)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Match with UUID " +
                                        matchUuid +
                                        " not found"
                        )
                );
    }

    private void saveMatch(UnfinishedMatch unfinishedMatch, UUID matchUuid) {
        String firstPlayerName = unfinishedMatch.getFirstPlayerScore().getName();
        String winnerName = unfinishedMatch.getWinnerName();

        int firstPlayerId = unfinishedMatch.getFirstPlayerScore().getId();
        int secondPlayerId = unfinishedMatch.getSecondPlayerScore().getId();

        int winnerId = firstPlayerName.equals(winnerName) ? firstPlayerId : secondPlayerId;

        matchRepository.saveFinishedMatch(
                firstPlayerId,
                secondPlayerId,
                winnerId
        );

        unfinishedMatchesStorage.remove(matchUuid);
    }
}
