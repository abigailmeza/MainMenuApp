package persistence;

import exceptions.IllegalNameException;
import model.Game;
import model.GameList;
import org.json.JSONObject;
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
            //
        } catch (IllegalNameException e) {
            fail("Exception should not be thrown");
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
        } catch (IllegalNameException e) {
            fail("Exception should not be thrown");
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
        } catch (IllegalNameException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void testReaderEmptyStringGameList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStringGameList.json");
        try {
            GameList gl = reader.read();
            fail("IllegalNameException should have been thrown");
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (IllegalNameException e) {
            //
        }
    }
}
