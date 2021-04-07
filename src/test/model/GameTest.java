package model;

import exceptions.IllegalNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game g1;
    private Game g2;

    @BeforeEach
    public void runBefore() {
        try {
            g1 = new Game("Game 1", "Hard");
            g2 = new Game("Game 2");
        } catch (IllegalNameException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testConstructors() {
        assertEquals("Game 1", g1.getName());
        assertEquals("Hard", g1.getLevel());
        assertEquals("Game 2", g2.getName());
        assertEquals("Easy", g2.getLevel());

    }

    @Test
    public void testSetName() {
        assertEquals("Game 2", g2.getName());
        g2.setName("Game 1");
        assertEquals("Game 1", g2.getName());
    }

    @Test
    public void testSetLevel() {
        assertEquals("Easy", g2.getLevel());
        g2.setLevel("Hard");
        assertEquals("Hard", g2.getLevel());
    }


    @Test
    public void testNewGameNoExceptionCaught() {
        try {
            new Game("Game 1","Easy");
            new Game ("Game 2");
        } catch (IllegalNameException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testGameIllegalLevelNameException() {
        try {
            new Game("Game","");
            fail("Exception should be thrown");
        } catch (IllegalNameException e) {
            //
        }
    }

    @Test
    public void testGameIllegalGameNameException() {
        try {
            new Game("","Easy");
            fail("Exception should be thrown");
        } catch (IllegalNameException e) {
            //
        }
    }

    @Test
    public void testGameIllegalBothNamesException() {
        try {
            new Game("","");
            fail("Exception should be thrown");
        } catch (IllegalNameException e) {
            //
        }
    }

    @Test
    public void testGameIllegalNameException() {
        try {
            new Game("");
            fail("Exception should be thrown");
        } catch (IllegalNameException e) {
            //
        }
    }


}