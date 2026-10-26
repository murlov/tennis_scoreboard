package murlov.tennis_scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

        winGameForPlayer("Kolya");

        assertAll(
                () -> assertNull(vasyaPlayerScore.getPoints(), "Regular points must not be used during a tie-break"),
                () -> assertNull(kolyaPlayerScore.getPoints(), "Regular points must not be used during a tie-break"),
                () -> assertNotNull(vasyaPlayerScore.getTieBreakPoints(), "Tiebreak point must be initialized when tiebreak starts"),
                () -> assertNotNull(kolyaPlayerScore.getTieBreakPoints(), "Tiebreak point must be initialized when tiebreak starts"),
                () -> assertTrue(unfinishedMatch.isTieBreakInSet(), "Tie-break flag must be true when tie-break starts")
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4})
    void addingGameAtSixAgainstLowerScoreShouldWinSet(int kolyaGames) {
        vasyaPlayerScore.setGames(5);
        kolyaPlayerScore.setGames(kolyaGames);

        winGameForPlayer("Vasya");

        assertAll(
                () -> assertEquals(GamePoints.ZERO, vasyaPlayerScore.getPoints(), "Game points must be zero"),
                () -> assertEquals(GamePoints.ZERO, kolyaPlayerScore.getPoints(), "Game points must be zero"),
                () -> assertEquals(0, vasyaPlayerScore.getGames(), "Games must be zero"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Games must be zero"),
                () -> assertEquals(1, vasyaPlayerScore.getSets(), "Vasya must have 1 set"),
                () -> assertEquals(0, kolyaPlayerScore.getSets(), "Kolya must not have any set")
        );
    }

    @Test
    void setMustBeWonAtSevenFiveWhenScoreIsFiveAll() {
        vasyaPlayerScore.setGames(5);
        kolyaPlayerScore.setGames(5);

        winGameForPlayer("Vasya");

        assertAll(
                () -> assertEquals(GamePoints.ZERO, vasyaPlayerScore.getPoints(), "Game points must be zero"),
                () -> assertEquals(GamePoints.ZERO, kolyaPlayerScore.getPoints(), "Game points must be zero"),
                () -> assertEquals(6, vasyaPlayerScore.getGames(), "Vasya must have 6 games"),
                () -> assertEquals(5, kolyaPlayerScore.getGames(), "Kolya must have 5 games"),
                () -> assertEquals(0, vasyaPlayerScore.getSets(), "Vasya must not have any set"),
                () -> assertEquals(0, kolyaPlayerScore.getSets(), "Kolya must not have any set")
        );

        winGameForPlayer("Vasya");

        assertAll(
                () -> assertEquals(GamePoints.ZERO, vasyaPlayerScore.getPoints(), "Game points must be zero"),
                () -> assertEquals(GamePoints.ZERO, kolyaPlayerScore.getPoints(), "Game points must be zero"),
                () -> assertEquals(0, vasyaPlayerScore.getGames(), "Vasya must have 0 games"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya must have 0 games"),
                () -> assertEquals(1, vasyaPlayerScore.getSets(), "Vasya must have 1 set"),
                () -> assertEquals(0, kolyaPlayerScore.getSets(), "Kolya must not have any set")
        );
    }

    @Test
    void winSetByWinningTieBreak() {
        vasyaPlayerScore.setGames(6);
        kolyaPlayerScore.setGames(5);

        winGameForPlayer("Kolya");
        winGameForPlayer("Kolya");

        assertAll(
                () -> assertEquals(GamePoints.ZERO, vasyaPlayerScore.getPoints(), "Game points must be zero"),
                () -> assertEquals(GamePoints.ZERO, kolyaPlayerScore.getPoints(), "Game points must be zero"),
                () -> assertNull(vasyaPlayerScore.getTieBreakPoints(), "Tie-break points must be null"),
                () -> assertNull(kolyaPlayerScore.getTieBreakPoints(), "Tie-break points must be null"),
                () -> assertFalse(unfinishedMatch.isTieBreakInSet(), "Tie-break flag must be false when tie-break end"),
                () -> assertEquals(0, vasyaPlayerScore.getGames(), "Vasya must have 0 games"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya must have 0 games"),
                () -> assertEquals(0, vasyaPlayerScore.getSets(), "Vasya must not have any set"),
                () -> assertEquals(1, kolyaPlayerScore.getSets(), "Kolya must have 1 set")
        );
    }

    private void winGameForPlayer(String name) {
        int n = unfinishedMatch.isTieBreakInSet() ? 7 : 4;
        for (int i = 0; i < n; i++) {
            unfinishedMatch.addPoint(name);
        }
    }
}
