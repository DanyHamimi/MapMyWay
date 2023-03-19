package fr.uparis.backapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Testeur de la classe Station
 */
public class TestStation {

    private Station station;
    private Coordonnee coordonnee;
    private Set<Section> correspondances;

    @BeforeEach
    public void setUp() {

        coordonnee = new Coordonnee(1, 2);
        correspondances = new HashSet<>();
        station = new Station("nomStation", coordonnee, correspondances);
    }

    @Test
    public void testGetNomStation() {
        assertEquals("nomStation", station.getNomStation());
    }

    @Test
    public void testSetNomStation() {
        station.setNomStation("newNomStation");
        assertEquals("newNomStation", station.getNomStation());
    }

    @Test
    public void testGetLocalisation() {
        assertEquals(coordonnee, station.getLocalisation());
    }

    @Test
    public void testSetLocalisation() {
        Coordonnee newCoordonnee = new Coordonnee(3, 4);
        station.setLocalisation(newCoordonnee);
        assertEquals(newCoordonnee, station.getLocalisation());
    }

    @Test
    public void testGetCorrespondances() {
        assertEquals(correspondances, station.getCorrespondances());
    }
    @Test
    public void testEquals() {
        Station station2 = new Station("nomStation", new Coordonnee(2, 3), new HashSet<>());
        Station station3 = new Station("nomStation2", new Coordonnee(1, 2), new HashSet<>());
        assertEquals(station, station2);
        assertNotEquals(station, station3);
    }

    @Test
    public void testHashCode() {
        Station station2 = new Station("nomStation", new Coordonnee(1, 2), new HashSet<>());
        assertEquals(station.hashCode(), station2.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("nomStation", station.toString());
    }
}
