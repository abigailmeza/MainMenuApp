package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamesTest {
    private Games games;
    private Game g1;
    private Game g2;
    private Game g3;

    @BeforeEach
    public void runBefore() {
        games = new Games();
        g1 = new Game("Game 1");
        g2 = new Game("Game 2");
        g3 = new Game("Game 3");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, games.size());
    }

    @Test
    public void testCreateNewGameOnce() {
        assertEquals(0,games.size());
        games.createNewGame(g1);
        assertEquals(1,games.size());
        assertTrue(games.contains(g1));
    }

    @Test
    public void testCreateNewGameTwice() {
        assertEquals(0,games.size());
        games.createNewGame(g1);
        games.createNewGame(g2);

        assertEquals(2,games.size());
        assertTrue(games.contains(g1));
        assertTrue(games.contains(g2));
    }

    @Test
    public void testCreateSameGame() {
        Game sameGame = new Game("Game 1");
        games.createNewGame(g1);
        assertTrue(games.contains(g1));
        assertEquals(1,games.size());

        games.createNewGame(sameGame);
        assertTrue(games.contains(g1));
        assertFalse(games.contains(sameGame));
        assertEquals(1, games.size());
    }

    @Test
    public void testCreateSameGameMultipleTimes() {
        Game game = new Game("Game");
        Game otherGame = new Game("Other Game");
        Game game2 = new Game("Game");
        Game otherGame2 = new Game ("Other Game");

        games.createNewGame(game);
        games.createNewGame(otherGame);
        assertEquals(2,games.size());

        games.createNewGame(game2);
        games.createNewGame(otherGame2);

        assertTrue(games.contains(game));
        assertTrue(games.contains(otherGame));
        assertFalse(games.contains(game2));
        assertFalse(games.contains(otherGame2));
        assertEquals(2, games.size());
    }

    @Test
    public void testHasSameName() {
        Game g4 = new Game("Game 4");
        Game sameGame = new Game("Game 3");

        games.createNewGame(g1);
        games.createNewGame(g2);
        games.createNewGame(g3);

        assertTrue(games.hasSameName(sameGame));
        assertFalse(games.hasSameName(g4));
    }

    @Test
    public void testDeleteNewGameOnce() {
        games.createNewGame(g1);
        assertTrue(games.contains(g1));

        games.deleteGame(g1);
        assertFalse(games.contains(g1));
    }

    @Test
    public void testDeleteNewGameTwice() {
        games.createNewGame(g1);
        games.createNewGame(g2);
        games.createNewGame(g3);

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
        games.createNewGame(g1);
        assertTrue(games.contains(g1));

        games.deleteGame(g2);
        assertTrue(games.contains(g1));
        assertEquals(1,games.size());
    }

    @Test
    public void testChangeGameName() {
        games.createNewGame(g1);
        games.createNewGame(g2);

        games.changeGameName(0,"New Name");
        assertEquals("New Name", g1.getName());
    }

    @Test
    public void testChangeGameNameTwice() {
        games.createNewGame(g1);
        games.createNewGame(g2);

        assertEquals("Game 1", g1.getName());
        games.changeGameName(0,"New Name");
        assertEquals("New Name", g1.getName());

        games.changeGameName(0,"Newer Name");
        assertEquals("Newer Name", g1.getName());
    }

    @Test
    public void testChangeGameLevel() {
        games.createNewGame(g1);
        games.createNewGame(g2);

        assertEquals("easy", g2.getLevel());
        games.changeGameLevel(1, "hard");
        assertEquals("hard", g2.getLevel());
    }
}
