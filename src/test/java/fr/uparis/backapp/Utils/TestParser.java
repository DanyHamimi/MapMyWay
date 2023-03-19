package fr.uparis.backapp.Utils;

import fr.uparis.backapp.model.Reseau;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Testeur des Parser du reseau et des horaires
 */
public class TestParser {

    @Test
    void testGetInstance() {
        Parser parser = Parser.getInstance();
        Parser parser1 = Parser.getInstance();
        assertSame(parser, parser1);
    }
    /**
     *
     */
    @Test
    public void testLectTime() {
    }

    /**
     *
     */
    @Test
    public void testLectNet() {
    }
}
