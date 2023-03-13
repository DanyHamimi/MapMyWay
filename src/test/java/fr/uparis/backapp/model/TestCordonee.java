package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testeur de la classe Coordonnee
 */
public class TestCordonee {

    /**
     * Teste la construction de la classe.
     * Teste les Getters de latitude et longitude
     */
    @Test
    public void testConstructorAndGetters() {
        Cordonnee cordonnee = new Cordonnee(48.8566, 2.3522);
        assertEquals(48.8566, cordonnee.getLatitude(), 0.0);
        assertEquals(2.3522, cordonnee.getLongitude(), 0.0);
    }

    /**
     * Teste les Setters sur latitude et longitude
     */
    @Test
    public void testSetters() {
        Cordonnee cordonnee = new Cordonnee(48.8566, 2.3522);
        cordonnee.setLatitude(45.75);
        cordonnee.setLongitude(4.85);
        assertEquals(45.75, cordonnee.getLatitude(), 0.0);
        assertEquals(4.85, cordonnee.getLongitude(), 0.0);
    }

    /**
     * Teste d'equals entre plusieurs Coordonnee
     */
    @Test
    public void testEquals() {
        Cordonnee cordonnee1 = new Cordonnee(48.8566, 2.3522);
        Cordonnee cordonnee2 = new Cordonnee(48.8566, 2.3522);
        Cordonnee cordonnee3 = new Cordonnee(45.75, 4.85);

        assertTrue(cordonnee1.equals(cordonnee2));
        assertFalse(cordonnee1.equals(cordonnee3));
        assertFalse(cordonnee1.equals(null));
        assertFalse(cordonnee1.equals("48.8566, 2.3522"));
    }
}
