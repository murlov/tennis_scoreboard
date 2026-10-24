package murlov.tennis_scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetTest {

    private PlayerScore vasyaPlayerScore;
    private PlayerScore kolyaPlayerScore;
    private UnfinishedMatch unfinishedMatch;

    @BeforeEach
    void setUp() {
        vasyaPlayerScore = new PlayerScore(1, "Vasya");
        kolyaPlayerScore = new PlayerScore(2, "Kolya");
        unfinishedMatch = new UnfinishedMatch(
                vasyaPlayerScore,
                kolyaPlayerScore
        );
    }

    @Test
    void shouldStartTieBreakWhenGameScoreIsSixAll() {
        vasyaPlayerScore.setGames(6);
        kolyaPlayerScore.setGames(5);
        vasyaPlayerScore.setPoints(GamePoints.ZERO);
        kolyaPlayerScore.setPoints(GamePoints.FORTY);

        unfinishedMatch.addPoint("Kolya");

        assertAll(
                () -> assertNull(vasyaPlayerScore.getPoints(), "Regular points should not be used during a tie-break"),
                () -> assertNull(kolyaPlayerScore.getPoints(), "Regular points should not be used during a tie-break"),
                () -> assertNotNull(vasyaPlayerScore.getTieBreakPoints(), "Tiebreak point must be initialized when tiebreak starts"),
                () -> assertNotNull(kolyaPlayerScore.getTieBreakPoints(), "Tiebreak point must be initialized when tiebreak starts"),
                () -> assertTrue(unfinishedMatch.isTieBreakInSet(), "Tie-break flag must be true when tie-break starts")
        );
    }
}
