package murlov.tennis_scoreboard.model;

import lombok.Data;
import murlov.tennis_scoreboard.exception.ValidationException;

import java.util.Optional;
import java.util.UUID;

@Data
public class UnfinishedMatch {
    private final UUID uuid;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
    private String winnerName;
    private boolean isTieBreakInSet;

    public UnfinishedMatch(PlayerScore firstPlayerScore,
                           PlayerScore secondPlayerScore) {
        uuid = UUID.randomUUID();
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }

    public void addPoint(String name) {
        PlayerScore playerScore = getPlayerScoreByName(name)
                .orElseThrow(() -> new ValidationException(
                        "Wrong player name"
                ));

        PlayerScore opponentPlayerScore = getOpponentPlayerScore(playerScore);

        if (isTieBreakInSet) {
            addTieBreakPoint(playerScore, opponentPlayerScore);
        } else {
            addGamePoint(playerScore, opponentPlayerScore);
        }
    }

    private void addTieBreakPoint(PlayerScore playerScore, PlayerScore opponentPlayerScore) {
        PointResult pointResult = Set.addTieBreakPoint(playerScore, opponentPlayerScore);

        if (pointResult == PointResult.WON) {
            playerScore.setSets(playerScore.getSets() + 1);
            if (playerScore.getSets() == 2) {
                winnerName = playerScore.getName();
            } else {
                isTieBreakInSet = false;
                playerScore.setPoints(GamePoints.ZERO);
                opponentPlayerScore.setPoints(GamePoints.ZERO);
                playerScore.setTieBreakPoints(null);
                opponentPlayerScore.setTieBreakPoints(null);
            }
        }
    }

    private void addGamePoint(PlayerScore playerScore, PlayerScore opponentPlayerScore) {

        PointResult pointResult = Set.addGamePoint(playerScore, opponentPlayerScore);

        if (pointResult == PointResult.WON) {
            playerScore.setSets(playerScore.getSets() + 1);
            if (playerScore.getSets() == 2) {
                winnerName = playerScore.getName();
            }

        } else if (pointResult == PointResult.TIEBREAK) {
            playerScore.setPoints(null);
            opponentPlayerScore.setPoints(null);
            playerScore.setTieBreakPoints(0);
            opponentPlayerScore.setTieBreakPoints(0);
            isTieBreakInSet = true;
        }
    }

    private Optional<PlayerScore> getPlayerScoreByName(String name) {
        if (firstPlayerScore.getName().equals(name)) {
            return Optional.of(firstPlayerScore);
        } else if (secondPlayerScore.getName().equals(name)) {
            return  Optional.of(secondPlayerScore);
        } else {
            return Optional.empty();
        }
    }

    private PlayerScore getOpponentPlayerScore(PlayerScore playerScore) {
        if (firstPlayerScore.equals(playerScore)) {
            return secondPlayerScore;
        } else {
            return  firstPlayerScore;
        }
    }
}
