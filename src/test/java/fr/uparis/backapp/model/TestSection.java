package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testeur de la classe Station
 */
public class TestSection {

    /**
     * Teste le Getter sur la Station de depart
     */
    @Test
    public void testGetStationDepart() {
        Station station1 = new Station("Station 1", new Cordonnee(0, 0), null);
        Station station2 = new Station("Station 2", new Cordonnee(1, 1), null);
        LocalTime duree = LocalTime.of(0, 5);
        double distance = 1.0;
        Ligne ligne = new Ligne("Ligne A", null, null);
        Section section = new Section(station1, station2, duree, distance, ligne);
        assertEquals(station1, section.getStationDepart());
    }

    /**
     * Teste le Setter sur la Station de depart
     */
    @Test
    public void testSetStationDepart() {
        Station station1 = new Station("Station 1", new Cordonnee(0, 0), null);
        Station station2 = new Station("Station 2", new Cordonnee(1, 1), null);
        LocalTime duree = LocalTime.of(0, 5);
        double distance = 1.0;
        Ligne ligne = new Ligne("Ligne A", null, null);
        Section section = new Section(station1, station2, duree, distance, ligne);
        section.setStationDepart(station2);
        assertEquals(station2, section.getStationDepart());
    }

    /**
     * Teste le Getter sur la Station d'arrivee
     */
    @Test
    public void testGetStationArrivee() {
        Station station1 = new Station("Station 1", new Cordonnee(0, 0), null);
        Station station2 = new Station("Station 2", new Cordonnee(1, 1), null);
        LocalTime duree = LocalTime.of(0, 5);
        double distance = 1.0;
        Ligne ligne = new Ligne("Ligne A", null, null);
        Section section = new Section(station1, station2, duree, distance, ligne);
        assertEquals(station2, section.getStationArrivee());
    }

    /**
     * Teste le Setter sur la Station d'arrivee
     */
    @Test
    public void testSetStationArrivee() {
        Station station1 = new Station("Station 1", new Cordonnee(0, 0), null);
        Station station2 = new Station("Station 2", new Cordonnee(1, 1), null);
        LocalTime duree = LocalTime.of(0, 5);
        double distance = 1.0;
        Ligne ligne = new Ligne("Ligne A", null, null);
        Section section = new Section(station1, station2, duree, distance, ligne);
        section.setStationArrivee(station1);
        assertEquals(station1, section.getStationArrivee());
    }

    /**
     * Teste le Getter sur la duree d'une Section
     */
    @Test
    public void testGetDuree() {
        Station station1 = new Station("Station 1", new Cordonnee(0, 0), null);
        Station station2 = new Station("Station 2", new Cordonnee(1, 1), null);
        LocalTime duree = LocalTime.of(0, 5);
        double distance = 1.0;
        Ligne ligne = new Ligne("Ligne A", null, null);
        Section section = new Section(station1, station2, duree, distance, ligne);
        assertEquals(duree, section.getDuree());
    }

    /**
     * Teste le Setter sur la duree de la Section
     */
    @Test
    public void testSetDuree() {
        Station stationDepart = new Station("Station A", new Cordonnee(0, 0), new ArrayList<>());
        Station stationArrivee = new Station("Station B", new Cordonnee(1, 1), new ArrayList<>());
        Ligne ligne = new Ligne("Ligne 1", new ArrayList<>(), new ArrayList<>());
        LocalTime duree = LocalTime.of(0, 30); // 30 minutes
        double distance = 10.0;
        Section section = new Section(stationDepart, stationArrivee, duree, distance, ligne);
        LocalTime newDuree = LocalTime.of(0, 45); // 45 minutes
        section.setDuree(newDuree);
        assertEquals(newDuree, section.getDuree());
    }

}
