package murlov.tennis_scoreboard.model;

public class Set {

    public static PointResult addGamePoint(PlayerScore playerScore, PlayerScore opponentPlayerScore) {

        PointResult pointResult = Game.addGamePoint(playerScore, opponentPlayerScore);

        if (pointResult == PointResult.IN_PROGRESS) {
            return PointResult.IN_PROGRESS;
        } else {
            playerScore.setGames(playerScore.getGames() + 1);
            if (isTieBreak(playerScore, opponentPlayerScore)) {
                return PointResult.TIEBREAK;
            }
            if (playerScore.getGames() == 7
                    && opponentPlayerScore.getGames() == 5) {
                playerScore.setGames(0);
                opponentPlayerScore.setGames(0);
                return PointResult.WON;
            } else if (playerScore.getGames() == 6
                    && opponentPlayerScore.getGames() < 5) {
                playerScore.setGames(0);
                opponentPlayerScore.setGames(0);
                return PointResult.WON;
            } else {
                return  PointResult.IN_PROGRESS;
            }
        }
    }

    public static PointResult addTieBreakPoint(PlayerScore playerScore, PlayerScore opponentPlayerScore) {
        PointResult pointResult = Game.addTieBreakPoint(playerScore, opponentPlayerScore);

        if (pointResult == PointResult.WON) {
            playerScore.setGames(0);
            opponentPlayerScore.setGames(0);
            return PointResult.WON;
        } else {
            return PointResult.IN_PROGRESS;
        }
    }

    private static boolean isTieBreak(PlayerScore playerScore, PlayerScore opponentPlayerScore) {
        return playerScore.getGames() == 6
                && opponentPlayerScore.getGames() == 6;
    }
}
