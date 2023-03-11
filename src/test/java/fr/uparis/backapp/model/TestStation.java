package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestStation {

    @Test
    public void testConstructorWithNullValues() {
        Station station = new Station(null, null, null);
        assertNotNull(station);
        assertEquals(null, station.getNomStation());
        assertEquals(null, station.getLocalisation());
        assertEquals(null, station.getCorrespondances());
    }

    @Test
    public void testConstructorWithEmptyValues() {
        List<Ligne> correspondances = new ArrayList<>();
        Station station = new Station("", new Cordonnee(0, 0), correspondances);
        assertNotNull(station);
        assertEquals("", station.getNomStation());
        assertEquals(new Cordonnee(0, 0), station.getLocalisation());
        assertEquals(correspondances, station.getCorrespondances());
    }

    @Test
    public void testConstructorWithNonEmptyValues() {
        List<Ligne> correspondances = Arrays.asList(new Ligne("L1", null, null), new Ligne("L2", null, null));
        Station station = new Station("S1", new Cordonnee(48.859865, 2.346949), correspondances);
        assertNotNull(station);
        assertEquals("S1", station.getNomStation());
        assertEquals(new Cordonnee(48.859865, 2.346949), station.getLocalisation());
        assertEquals(correspondances, station.getCorrespondances());
    }

    @Test
    public void testGetNomStation() {
        Station station = new Station("S1", new Cordonnee(0, 0), null);
        assertEquals("S1", station.getNomStation());
    }

    @Test
    public void testSetNomStation() {
        Station station = new Station("S1", new Cordonnee(0, 0), null);
        station.setNomStation("S2");
        assertEquals("S2", station.getNomStation());
    }

    @Test
    public void testGetLocalisation() {
        Cordonnee cordonnee = new Cordonnee(48.859865, 2.346949);
        Station station = new Station("S1", cordonnee, null);
        assertEquals(cordonnee, station.getLocalisation());
    }

    @Test
    public void testSetLocalisation() {
        Cordonnee cordonnee1 = new Cordonnee(48.859865, 2.346949);
        Cordonnee cordonnee2 = new Cordonnee(48.858373, 2.294481);
        Station station = new Station("S1", cordonnee1, null);
        station.setLocalisation(cordonnee2);
        assertEquals(cordonnee2, station.getLocalisation());
    }

    @Test
    public void testGetCorrespondances() {
        List<Ligne> correspondances = Arrays.asList(new Ligne("L1", null, null), new Ligne("L2", null, null));
        Station station = new Station("S1", new Cordonnee(0, 0), correspondances);
        assertEquals(correspondances, station.getCorrespondances());
    }

    @Test
    public void testSetCorrespondances() {
        List<Ligne> correspondances1 = Arrays.asList(new Ligne("L1", null, null), new Ligne("L2", null, null));
        List<Ligne> correspondances2 = Arrays.asList(new Ligne("L1", null, null), new Ligne("L3", null, null));
        Station station = new Station("S1", new Cordonnee(0, 0), correspondances1);
        station.setCorrespondances(correspondances2);
        assertEquals(correspondances2, station.getCorrespondances());
    }
}
