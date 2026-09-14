package murlov.tennis_scoreboard.model;

public class Set {

    public static PointResult addPoint(PlayerScore playerScore, PlayerScore opponentPlayerScore) {

        PointResult pointResult = Game.addPoint(playerScore, opponentPlayerScore);

        if (pointResult == PointResult.IN_PROGRESS) {
            return PointResult.IN_PROGRESS;
        } else {
            if (playerScore.getGames() == 5) {
                playerScore.setGames(0);
                return PointResult.WON;
            } else {
                playerScore.setGames(playerScore.getGames() + 1);
                return  PointResult.IN_PROGRESS;
            }
        }
    }
}
