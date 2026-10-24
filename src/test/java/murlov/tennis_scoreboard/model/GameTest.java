package murlov.tennis_scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

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
    void addingPointAtFortyFortyShouldGiveAdvantage() {
        vasyaPlayerScore.setPoints(GamePoints.FORTY);
        kolyaPlayerScore.setPoints(GamePoints.FORTY);

        unfinishedMatch.addPoint("Kolya");

        assertAll(
                () -> assertEquals(GamePoints.ADVANTAGE, kolyaPlayerScore.getPoints(), "Kolya must have advantage"),
                () -> assertEquals(GamePoints.FORTY, vasyaPlayerScore.getPoints(), "Vasya must have forty points"),
                () -> assertEquals(0, vasyaPlayerScore.getGames(), "Vasya must have zero games"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya must have zero games")
        );
    }

    @ParameterizedTest
    @EnumSource(
            value = GamePoints.class,
            names = {"ZERO", "FIFTEEN", "THIRTY"}
    )
    void addingPointAtFortyAgainstLowerScoreShouldWinGame(GamePoints points) {
        vasyaPlayerScore.setPoints(GamePoints.FORTY);
        kolyaPlayerScore.setPoints(points);

        unfinishedMatch.addPoint("Vasya");

        assertAll(
                () -> assertEquals(GamePoints.ZERO, vasyaPlayerScore.getPoints(), "Vasya's points must reset to zero after winning the game"),
                () -> assertEquals(GamePoints.ZERO, kolyaPlayerScore.getPoints(), "Kolya's points must reset to zero after Vasya wins the game"),
                () -> assertEquals(1, vasyaPlayerScore.getGames(), "Vasya must have one game"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya must have zero games")
        );
    }


    @Test
    void addingPointAtAdvantageAgainstFortyShouldWinGame() {
        vasyaPlayerScore.setPoints(GamePoints.ADVANTAGE);
        kolyaPlayerScore.setPoints(GamePoints.FORTY);

        unfinishedMatch.addPoint("Vasya");

        assertAll(
                () -> assertEquals(GamePoints.ZERO, vasyaPlayerScore.getPoints(), "Vasya's points must reset to zero after winning the game"),
                () -> assertEquals(GamePoints.ZERO, kolyaPlayerScore.getPoints(), "Kolya's points must reset to zero after Vasya wins the game"),
                () -> assertEquals(1, vasyaPlayerScore.getGames(), "Vasya must have one game"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya must have zero games")
        );
    }

    @Test
    void addingPointAtAdvantageAgainstFortyShouldGiveFortyForty() {
        vasyaPlayerScore.setPoints(GamePoints.ADVANTAGE);
        kolyaPlayerScore.setPoints(GamePoints.FORTY);

        unfinishedMatch.addPoint("Kolya");

        assertAll(
                () -> assertEquals(GamePoints.FORTY, vasyaPlayerScore.getPoints(), "Vasya must have forty points"),
                () -> assertEquals(GamePoints.FORTY, kolyaPlayerScore.getPoints(), "Kolya must have forty points"),
                () -> assertEquals(0, vasyaPlayerScore.getGames(), "Vasya must have zero games"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya must have zero games")
        );
    }
}