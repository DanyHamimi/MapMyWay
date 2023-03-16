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
        Coordonnee coordonnee= new Coordonnee(48.8566, 2.3522);
        assertEquals(48.8566, coordonnee.getLatitude(), 0.0);
        assertEquals(2.3522, coordonnee.getLongitude(), 0.0);
    }

    /**
     * Teste les Setters sur latitude et longitude
     */
    @Test
    public void testSetters() {
        Coordonnee coordonnee= new Coordonnee(48.8566, 2.3522);
        coordonnee.setLatitude(45.75);
        coordonnee.setLongitude(4.85);
        assertEquals(45.75, coordonnee.getLatitude(), 0.0);
        assertEquals(4.85, coordonnee.getLongitude(), 0.0);
    }

    /**
     * Teste d'equals entre plusieurs Coordonnee
     */
    @Test
    public void testEquals() {
        Coordonnee coordonnee1= new Coordonnee(48.8566, 2.3522);
        Coordonnee coordonnee2= new Coordonnee(48.8566, 2.3522);
        Coordonnee coordonnee3= new Coordonnee(45.75, 4.85);

        assertTrue(coordonnee1.equals(coordonnee2));
        assertFalse(coordonnee1.equals(coordonnee3));
        assertFalse(coordonnee1.equals(null));
        assertFalse(coordonnee1.equals("48.8566, 2.3522"));
    }
}
