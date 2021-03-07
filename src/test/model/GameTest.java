package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game g;

    @BeforeEach
    public void runBefore() {
        g = new Game("Untitled", "Easy");
    }

    @Test
    public void testConstructor() {
        assertEquals("Untitled", g.getName());
        assertEquals("Easy", g.getLevel());
    }

    @Test
    public void testSetName() {
        assertEquals("Untitled", g.getName());
        g.setName("Game 1");
        assertEquals("Game 1", g.getName());
    }

    @Test
    public void testSetLevel() {
        assertEquals("Easy", g.getLevel());
        g.setLevel("Hard");
        assertEquals("Hard", g.getLevel());
    }


}