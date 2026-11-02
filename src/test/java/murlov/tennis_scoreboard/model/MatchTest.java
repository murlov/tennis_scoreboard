package murlov.tennis_scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

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

    @ParameterizedTest
    @ValueSource(
            ints = {0, 1}
    )
    void winMatch(int sets) {
        vasyaPlayerScore.setSets(1);
        kolyaPlayerScore.setSets(sets);

        vasyaPlayerScore.setGames(5);
        winGameForPlayer("Vasya");

        assertAll(
                () -> assertEquals(GamePoints.ZERO, vasyaPlayerScore.getPoints(), "Vasya must not have any points"),
                () -> assertEquals(GamePoints.ZERO, kolyaPlayerScore.getPoints(), "Kolya must not have any points"),
                () -> assertEquals(0, vasyaPlayerScore.getGames(), "Vasya must not have any games"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya must not have any games"),
                () -> assertEquals(2, vasyaPlayerScore.getSets(), "Vasya must have 2 sets"),
                () -> assertEquals(sets, kolyaPlayerScore.getSets(), "Kolya must have" + sets + " sets"),
                () -> assertEquals("Vasya", unfinishedMatch.getWinnerName(), "Vasya must be winner")
        );
    }

    private void winGameForPlayer(String name) {
        int n = unfinishedMatch.isTieBreakInSet() ? 7 : 4;
        for (int i = 0; i < n; i++) {
            unfinishedMatch.addPoint(name);
        }
    }
}
