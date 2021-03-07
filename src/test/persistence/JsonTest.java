package persistence;

import model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Citation: Based on the CPSC 210 JsonSerializationDemo
public class JsonTest {
    protected void checkGame(String name, String level, Game game) {
        assertEquals(name, game.getName());
        assertEquals(level,game.getLevel());
    }
}
