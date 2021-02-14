package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game g;

    @BeforeEach
    public void runBefore() {
        g = new Game("Untitled");
    }

    @Test
    public void testConstructor() {
        assertEquals("Untitled", g.getName());
        assertEquals("easy", g.getLevel());
    }

    @Test
    public void testSetName() {
        assertEquals("Untitled", g.getName());
        g.setName("Game 1");
        assertEquals("Game 1", g.getName());
    }

    @Test
    public void testSetLevel() {
        assertEquals("easy", g.getLevel());
        g.setLevel("hard");
        assertEquals("hard", g.getLevel());
    }


}