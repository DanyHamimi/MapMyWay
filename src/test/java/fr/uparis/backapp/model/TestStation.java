package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testeur de la classe Station
 */
public class TestStation {

    /**
     * Teste de la construction d'une Station avec des parametres null
     */
    @Test
    public void testConstructorWithNullValues() {
        Station station = new Station(null, null, null);
        assertNotNull(station);
        assertEquals(null, station.getNomStation());
        assertEquals(null, station.getLocalisation());
        assertEquals(null, station.getCorrespondances());
    }

    /**
     * Teste la construction d'une classe avec des parametres vides
     */
    @Test
    public void testConstructorWithEmptyValues() {
        List<Ligne> correspondances = new ArrayList<>();
        Station station = new Station("", new Coordonnee(0, 0), correspondances);
        assertNotNull(station);
        assertEquals("", station.getNomStation());
        assertEquals(new Coordonnee(0, 0), station.getLocalisation());
        assertEquals(correspondances, station.getCorrespondances());
    }

    /**
     * Teste la construction d'une Station avec des parametres corrects
     */
    @Test
    public void testConstructorWithNonEmptyValues() {
        List<Ligne> correspondances = Arrays.asList(new Ligne("L1", null, null), new Ligne("L2", null, null));
        Station station = new Station("S1", new Coordonnee(48.859865, 2.346949), correspondances);
        assertNotNull(station);
        assertEquals("S1", station.getNomStation());
        assertEquals(new Coordonnee(48.859865, 2.346949), station.getLocalisation());
        assertEquals(correspondances, station.getCorrespondances());
    }

    /**
     * Teste le Getter de nomStation
     */
    @Test
    public void testGetNomStation() {
        Station station = new Station("S1", new Coordonnee(0, 0), null);
        assertEquals("S1", station.getNomStation());
    }

    /**
     * Teste le Setter de nomStation
     */
    @Test
    public void testSetNomStation() {
        Station station = new Station("S1", new Coordonnee(0, 0), null);
        station.setNomStation("S2");
        assertEquals("S2", station.getNomStation());
    }

    /**
     * Teste le Getter de Coordonnee d'une Station
     */
    @Test
    public void testGetLocalisation() {
        Coordonnee coordonnee= new Coordonnee(48.859865, 2.346949);
        Station station = new Station("S1", coordonnee, null);
        assertEquals(coordonnee, station.getLocalisation());
    }

    /**
     * Teste le Seter de Coordonnee d'une Station
     */
    @Test
    public void testSetLocalisation() {
        Coordonnee coordonnee1= new Coordonnee(48.859865, 2.346949);
        Coordonnee coordonnee2= new Coordonnee(48.858373, 2.294481);
        Station station = new Station("S1", coordonnee1, null);
        station.setLocalisation(coordonnee2);
        assertEquals(coordonnee2, station.getLocalisation());
    }

    /**
     * Teste le Getter sur la liste des correspondances d'une Station
     */
    @Test
    public void testGetCorrespondances() {
        List<Ligne> correspondances = Arrays.asList(new Ligne("L1", null, null), new Ligne("L2", null, null));
        Station station = new Station("S1", new Coordonnee(0, 0), correspondances);
        assertEquals(correspondances, station.getCorrespondances());
    }

    /**
     * Teste le Setter sur la liste des correspondances d'une' Station
     */
    @Test
    public void testSetCorrespondances() {
        List<Ligne> correspondances1 = Arrays.asList(new Ligne("L1", null, null), new Ligne("L2", null, null));
        List<Ligne> correspondances2 = Arrays.asList(new Ligne("L1", null, null), new Ligne("L3", null, null));
        Station station = new Station("S1", new Coordonnee(0, 0), correspondances1);
        station.setCorrespondances(correspondances2);
        assertEquals(correspondances2, station.getCorrespondances());
    }
}
