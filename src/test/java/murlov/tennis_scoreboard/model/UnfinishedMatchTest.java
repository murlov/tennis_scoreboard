package murlov.tennis_scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class UnfinishedMatchTest {

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
    void pointAtFortyFortyShouldGiveAdvantage() {
        vasyaPlayerScore.setPoints(GamePoints.FORTY);
        kolyaPlayerScore.setPoints(GamePoints.FORTY);

        unfinishedMatch.addPoint("Kolya");

        assertAll(
                () -> assertEquals(GamePoints.ADVANTAGE, kolyaPlayerScore.getPoints(), "Kolya should have advantage"),
                () -> assertEquals(GamePoints.FORTY, vasyaPlayerScore.getPoints(), "Vasya should have forty points"),
                () -> assertEquals(0, vasyaPlayerScore.getGames(), "Vasya should have zero games"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya should have zero games")
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
                () -> assertEquals(GamePoints.ZERO, vasyaPlayerScore.getPoints(), "Vasya's points should reset to zero after winning the game"),
                () -> assertEquals(GamePoints.ZERO, kolyaPlayerScore.getPoints(), "Kolya's points should reset to zero after Vasya wins the game"),
                () -> assertEquals(1, vasyaPlayerScore.getGames(), "Vasya should have one game"),
                () -> assertEquals(0, kolyaPlayerScore.getGames(), "Kolya should have zero games")
        );
    }


}