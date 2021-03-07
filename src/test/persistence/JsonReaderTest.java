package persistence;

import model.Game;
import model.GameList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Citation: Based on the CPSC 210 JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameList gl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGameList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGameList.json");
        try {
            GameList gl = reader.read();
            assertEquals("My saved games", gl.getGameListName());
            assertEquals(0, gl.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGameList() {
        JsonReader reader = new JsonReader("./data/testReaderGameList.json");
        try {
            GameList gl = reader.read();
            assertEquals("My saved games", gl.getGameListName());
            List<Game> savedGames = gl.getGames();
            assertEquals(2, savedGames.size());
            checkGame("Game 1", "Easy", savedGames.get(0));
            checkGame("Game 2", "Easy", savedGames.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
