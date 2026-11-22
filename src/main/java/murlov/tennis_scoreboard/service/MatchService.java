package murlov.tennis_scoreboard.service;

import lombok.RequiredArgsConstructor;
import murlov.tennis_scoreboard.exception.MatchAlreadyFinishedException;
import murlov.tennis_scoreboard.model.domain.FinishedMatch;
import murlov.tennis_scoreboard.model.domain.Player;
import murlov.tennis_scoreboard.repository.MatchRepository;
import murlov.tennis_scoreboard.repository.PlayerRepository;
import murlov.tennis_scoreboard.dto.NewMatchRequestDto;
import murlov.tennis_scoreboard.dto.PointRequestDto;
import murlov.tennis_scoreboard.exception.NotFoundException;
import murlov.tennis_scoreboard.model.domain.PlayerScore;
import murlov.tennis_scoreboard.model.domain.UnfinishedMatch;
import murlov.tennis_scoreboard.storage.UnfinishedMatchesStorage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final UnfinishedMatchesStorage unfinishedMatchesStorage;

    public UUID createMatch(NewMatchRequestDto newMatchRequestDto) {
        Player firstPlayer = playerRepository
                .getByName(newMatchRequestDto.firstPlayerName())
                .orElseGet(() -> playerRepository.save(
                        newMatchRequestDto.firstPlayerName()
                ));

        Player secondPlayer = playerRepository
                .getByName(newMatchRequestDto.secondPlayerName())
                .orElseGet(() -> playerRepository.save(
                        newMatchRequestDto.secondPlayerName()
                ));

        UnfinishedMatch unfinishedMatch = new UnfinishedMatch(
                new PlayerScore(firstPlayer.id(), firstPlayer.name()),
                new PlayerScore(secondPlayer.id(), secondPlayer.name())
        );

        unfinishedMatchesStorage.save(unfinishedMatch);

        return unfinishedMatch.getUuid();
    }

    public UnfinishedMatch addPoint(UUID matchUuid, PointRequestDto pointRequestDto) {
        UnfinishedMatch unfinishedMatch = getUnfinishedMatch(matchUuid);

        synchronized (unfinishedMatch) {
            if (unfinishedMatchesStorage.isExists(unfinishedMatch)) {
                unfinishedMatch.addPoint(pointRequestDto.name());

                if (unfinishedMatch.getWinnerName() != null) {
                    saveMatch(unfinishedMatch, matchUuid);
                }
            } else {
                throw new MatchAlreadyFinishedException(
                        "Math with UUID " + matchUuid + " is already finished"
                );
            }
        }

        return unfinishedMatch;
    }

    public UnfinishedMatch getUnfinishedMatch(UUID matchUuid) {
        return unfinishedMatchesStorage.get(matchUuid)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Match with UUID " +
                                        matchUuid +
                                        " not found"
                        )
                );
    }

    public List<FinishedMatch> getFinishedMatches(Integer page, String playerName) {
        if (page == null) {
            page = 1;
        }

        if (playerName == null) {
            return matchRepository.getFinishedMatches(page);
        } else {
            return matchRepository.getFinishedMatchesByName(page, playerName);
        }
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
