package murlov.tennis_scoreboard.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GamePoints {
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    ADVANTAGE("AD");

    private final String value;

    GamePoints(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
