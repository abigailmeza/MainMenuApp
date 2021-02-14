package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameListTest {
    private GameList games;
    private Game g1;
    private Game g2;
    private Game g3;

    @BeforeEach
    public void runBefore() {
        games = new GameList();
        g1 = new Game("Game 1");
        g2 = new Game("Game 2");
        g3 = new Game("Game 3");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, games.size());
    }

    @Test
    public void testAddGameOnce() {
        assertEquals(0,games.size());
        games.addGame(g1);
        assertEquals(1,games.size());
        assertTrue(games.contains(g1));
    }

    @Test
    public void testAddGameTwice() {
        assertEquals(0,games.size());
        games.addGame(g1);
        games.addGame(g2);

        assertEquals(2,games.size());
        assertTrue(games.contains(g1));
        assertTrue(games.contains(g2));
    }

    @Test
    public void testAddGameWithSameName() {
        Game sameGame = new Game("Game 1");
        games.addGame(g1);
        assertTrue(games.contains(g1));
        assertEquals(1,games.size());

        games.addGame(sameGame);
        assertTrue(games.contains(g1));
        assertFalse(games.contains(sameGame));
        assertEquals(1, games.size());
    }

    @Test
    public void testAddGameWithSameNameMultipleTimes() {
        Game game = new Game("Game");
        Game otherGame = new Game("Other Game");
        Game game2 = new Game("Game");
        Game otherGame2 = new Game ("Other Game");

        games.addGame(game);
        games.addGame(otherGame);
        assertEquals(2,games.size());

        games.addGame(game2);
        games.addGame(otherGame2);

        assertTrue(games.contains(game));
        assertTrue(games.contains(otherGame));
        assertFalse(games.contains(game2));
        assertFalse(games.contains(otherGame2));
        assertEquals(2, games.size());
    }

    @Test
    public void testAddGameIdenticalGame() {
        assertEquals(0,games.size());
        games.addGame(g1);
        games.addGame(g1);

        assertEquals(1,games.size());
        assertTrue(games.contains(g1));
    }

    @Test
    public void testDeleteGameOnce() {
        games.addGame(g1);
        assertTrue(games.contains(g1));

        games.deleteGame(g1);
        assertFalse(games.contains(g1));
    }

    @Test
    public void testDeleteGameTwice() {
        games.addGame(g1);
        games.addGame(g2);
        games.addGame(g3);

        assertTrue(games.contains(g1));
        assertTrue(games.contains(g2));
        assertTrue(games.contains(g3));

        games.deleteGame(g1);
        games.deleteGame(g3);
        assertFalse(games.contains(g1));
        assertTrue(games.contains(g2));
        assertFalse(games.contains(g3));
        assertEquals(1, games.size());
    }

    @Test
    public void testDeleteGameNotInList() {
        games.addGame(g1);
        assertTrue(games.contains(g1));

        games.deleteGame(g2);
        assertTrue(games.contains(g1));
        assertEquals(1,games.size());
    }

    @Test
    public void testChangeGameName() {
        games.addGame(g1);
        games.addGame(g2);

        games.changeGameName("Game 1","New Name");
        assertEquals("New Name", g1.getName());
    }

    @Test
    public void testChangeGameNameTwice() {
        games.addGame(g1);
        games.addGame(g2);

        assertEquals("Game 1", g1.getName());
        games.changeGameName("Game 1","New Name");
        assertEquals("New Name", g1.getName());

        games.changeGameName("New Name","Newer Name");
        assertEquals("Newer Name", g1.getName());
    }

    @Test
    public void testChangeGameLevel() {
        games.addGame(g1);
        games.addGame(g2);
        games.addGame(g3);

        assertEquals("easy", g2.getLevel());
        games.changeGameLevel("Game 2", "hard");
        assertEquals("hard", g2.getLevel());
    }

    @Test
    public void testChangeGameLevelTwice() {
        games.addGame(g1);
        games.addGame(g2);
        games.addGame(g3);

        assertEquals("easy", g2.getLevel());
        games.changeGameLevel("Game 2", "hard");
        assertEquals("hard", g2.getLevel());
        games.changeGameLevel("Game 3", "easy");
        assertEquals("easy", g3.getLevel());
    }

    @Test
    public void testGetGameWithName() {
        games.addGame(g1);
        games.addGame(g2);
        games.addGame(g3);

        assertEquals(g3, games.getGameWithName("Game 3"));
        assertEquals(g1, games.getGameWithName("Game 1"));
    }

    @Test
    public void testGetGameWithNameNonExistent() {
        games.addGame(g1);
        assertEquals(null, games.getGameWithName("Fake Game"));
    }

    @Test
    public void testViewGameNamesOneName() {
        ArrayList<String> gameNames = new ArrayList<>();
        gameNames.add("Game 2");
        games.addGame(g2);

        assertEquals(gameNames, games.viewGameNames());
    }

    @Test
    public void testViewGameNamesEmpty() {
        ArrayList<String> gameNames = new ArrayList<>();

        assertEquals(gameNames, games.viewGameNames());
    }

    @Test
    public void testViewGameNamesMultipleNames() {
        ArrayList<String> gameNames = new ArrayList<>();
        gameNames.add("Game 3");
        gameNames.add("Game 2");
        gameNames.add("Game 1");

        games.addGame(g3);
        games.addGame(g2);
        games.addGame(g1);

        assertEquals(gameNames, games.viewGameNames());
    }

}
