package fr.uparis.backapp.utils.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testeur de la classe Constants.
 */
public class TestConstants {
    /**
     * Teste la création de la classe.
     */
    @Test
    public void testCreate() {
        Constants constants = new Constants();
        assertNotNull(constants);
    }
}