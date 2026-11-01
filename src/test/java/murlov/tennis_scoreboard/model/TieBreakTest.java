package murlov.tennis_scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TieBreakTest {
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
    void tieBreakAtSevenSixMustContinue() {
        vasyaPlayerScore.setGames(6);
        kolyaPlayerScore.setGames(5);

        winGameForPlayer("Kolya");

        vasyaPlayerScore.setTieBreakPoints(6);
        kolyaPlayerScore.setTieBreakPoints(6);
        unfinishedMatch.addPoint("Vasya");

        assertAll(
                () -> assertEquals(7, vasyaPlayerScore.getTieBreakPoints(), "Vasya must have 7 tie-break points"),
                () -> assertEquals(6, kolyaPlayerScore.getTieBreakPoints(), "Kolya must have 6 tie-break points"),
                () -> assertEquals(6, vasyaPlayerScore.getGames(), "Vasya's games score must not change"),
                () -> assertEquals(6, kolyaPlayerScore.getGames(), "Kolya's games score must not change"),
                () -> assertTrue(unfinishedMatch.isTieBreakInSet(), "Tie-break must continue")
        );
    }

    private void winGameForPlayer(String name) {
        int n = unfinishedMatch.isTieBreakInSet() ? 7 : 4;
        for (int i = 0; i < n; i++) {
            unfinishedMatch.addPoint(name);
        }
    }
}
