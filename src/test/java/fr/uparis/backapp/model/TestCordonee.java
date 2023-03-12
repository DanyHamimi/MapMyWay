package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCordonee {

    @Test
    public void testConstructorAndGetters() {
        Cordonnee cordonnee = new Cordonnee(48.8566, 2.3522);
        assertEquals(48.8566, cordonnee.getLatitude(), 0.0);
        assertEquals(2.3522, cordonnee.getLongitude(), 0.0);
    }

    @Test
    public void testSetters() {
        Cordonnee cordonnee = new Cordonnee(48.8566, 2.3522);
        cordonnee.setLatitude(45.75);
        cordonnee.setLongitude(4.85);
        assertEquals(45.75, cordonnee.getLatitude(), 0.0);
        assertEquals(4.85, cordonnee.getLongitude(), 0.0);
    }

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
