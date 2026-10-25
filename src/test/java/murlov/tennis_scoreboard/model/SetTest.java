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

    private void winGameForPlayer(String name) {
        for (int i = 0; i < 4; i++) {
            unfinishedMatch.addPoint(name);
        }
    }
}
