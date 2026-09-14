package murlov.tennis_scoreboard.model;

import murlov.tennis_scoreboard.exception.ValidationException;

public class Game {
    public static PointResult addPoint(PlayerScore playerScore, PlayerScore opponentPlayerScore) {
        switch (playerScore.getPoints()) {
            case GamePoints.ZERO:
                playerScore.setPoints(GamePoints.FIFTEEN);
                return PointResult.IN_PROGRESS;
            case GamePoints.FIFTEEN:
                playerScore.setPoints(GamePoints.THIRTY);
                return PointResult.IN_PROGRESS;
            case GamePoints.THIRTY:
                playerScore.setPoints(GamePoints.FORTY);
                return  PointResult.IN_PROGRESS;
            case GamePoints.FORTY:
                switch (opponentPlayerScore.getPoints()) {
                    case GamePoints.FORTY:
                        playerScore.setPoints(GamePoints.ADVANTAGE);
                        return PointResult.IN_PROGRESS;
                    case GamePoints.ADVANTAGE:
                        opponentPlayerScore.setPoints(GamePoints.FORTY);
                        return PointResult.IN_PROGRESS;
                    case GamePoints.ZERO:
                    case GamePoints.FIFTEEN:
                    case GamePoints.THIRTY:
                        playerScore.setPoints(GamePoints.ZERO);
                        opponentPlayerScore.setPoints(GamePoints.ZERO);
                        return PointResult.WON;
                    default:
                        throw new ValidationException(
                                "Unexcepted points number"
                        );
                }
            case GamePoints.ADVANTAGE:
                playerScore.setPoints(GamePoints.ZERO);
                opponentPlayerScore.setPoints(GamePoints.ZERO);
                return PointResult.WON;
            default:
                throw new ValidationException(
                        "Unexcepted points number"
                );
        }
    }
}
