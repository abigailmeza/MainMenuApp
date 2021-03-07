package persistence;

import model.Game;
import model.GameList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Citation: Based on the CPSC 210 JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            GameList gl = new GameList("My saved games");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testWriterEmptyWorkroom() {
        try {
            GameList gl = new GameList("My saved games");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGameList.json");
            writer.open();
            writer.write(gl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGameList.json");
            gl = reader.read();
            assertEquals("My saved games", gl.getGameListName());
            assertEquals(0, gl.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGameList() {
        try {
            GameList gl = new GameList("My saved games");
            gl.addGame(new Game("Game 1", "Easy"));
            gl.addGame(new Game("Game 2", "Easy"));
            JsonWriter writer = new JsonWriter("./data/testWriterGameList.json");
            writer.open();
            writer.write(gl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGameList.json");
            gl = reader.read();
            assertEquals("My saved games", gl.getGameListName());
            List<Game> savedGames = gl.getGames();
            assertEquals(2, gl.size());
            checkGame("Game 1", "Easy", savedGames.get(0));
            checkGame("Game 2", "Easy", savedGames.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
