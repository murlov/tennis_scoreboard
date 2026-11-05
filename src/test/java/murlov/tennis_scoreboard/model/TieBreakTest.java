package murlov.tennis_scoreboard.model;

import murlov.tennis_scoreboard.model.domain.GamePoints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TieBreakTest extends AbstractTennisTest{

    @BeforeEach
    void setUpTieBreak() {
        setUp();
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

    @Test
    void winTieBreakAtSevenFive() {
        vasyaPlayerScore.setGames(6);
        kolyaPlayerScore.setGames(5);

        winGameForPlayer("Kolya");

        vasyaPlayerScore.setTieBreakPoints(6);
        kolyaPlayerScore.setTieBreakPoints(5);
        unfinishedMatch.addPoint("Vasya");

        assertAll(
                () -> assertNull(vasyaPlayerScore.getTieBreakPoints(), "Vasya's tie-break points must be null"),
                () -> assertNull(kolyaPlayerScore.getTieBreakPoints(), "Kolya's tie-break points must be null"),
                () -> assertEquals(GamePoints.ZERO, vasyaPlayerScore.getPoints(), "Vasya must have 0 points"),
                () -> assertEquals(GamePoints.ZERO, kolyaPlayerScore.getPoints(), "Kolya must have 0 points"),
                () -> assertEquals(0, vasyaPlayerScore.getGames(), "Vasya must have 0 games"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya must have 0 games"),
                () -> assertEquals(1, vasyaPlayerScore.getSets(), "Vasya must have 1 set"),
                () -> assertEquals(0, kolyaPlayerScore.getSets(), "Kolya must not have any set"),
                () -> assertFalse(unfinishedMatch.isTieBreakInSet(), "Tie-break must finish")
        );
    }
}
