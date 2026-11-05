package murlov.tennis_scoreboard.model;

import murlov.tennis_scoreboard.model.domain.PlayerScore;
import murlov.tennis_scoreboard.model.domain.UnfinishedMatch;

public abstract class AbstractTennisTest {

    protected PlayerScore vasyaPlayerScore;
    protected PlayerScore kolyaPlayerScore;
    protected UnfinishedMatch unfinishedMatch;

    protected void setUp() {
        vasyaPlayerScore = new PlayerScore(1, "Vasya");
        kolyaPlayerScore = new PlayerScore(2, "Kolya");
        unfinishedMatch = new UnfinishedMatch(
                vasyaPlayerScore,
                kolyaPlayerScore
        );
    }

    protected void winGameForPlayer(String name) {
        int n = unfinishedMatch.isTieBreakInSet() ? 7 : 4;
        for (int i = 0; i < n; i++) {
            unfinishedMatch.addPoint(name);
        }
    }
}
