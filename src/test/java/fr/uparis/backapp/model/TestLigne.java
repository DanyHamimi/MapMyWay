package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestLigne {

    @Test
    public void testConstructorWithNullValues() {
        Ligne ligne = new Ligne(null, null, null);
        assertNotNull(ligne);
        assertEquals(null, ligne.getNomLigne());
        assertEquals(null, ligne.getStations());
        assertEquals(null, ligne.getTempsDeparts());
    }

    @Test
    public void testConstructorWithEmptyValues() {
        List<Station> stations = new ArrayList<>();
        List<LocalTime> tempsDeparts = new ArrayList<>();
        Ligne ligne = new Ligne("", stations, tempsDeparts);
        assertNotNull(ligne);
        assertEquals("", ligne.getNomLigne());
        assertEquals(stations, ligne.getStations());
        assertEquals(tempsDeparts, ligne.getTempsDeparts());
    }

    @Test
    public void testConstructorWithNonEmptyValues() {
        List<Station> stations = Arrays.asList(new Station("S1",null,new ArrayList<>()), new Station("S2",null,new ArrayList<>()));
        List<LocalTime> tempsDeparts = Arrays.asList(LocalTime.of(8, 0), LocalTime.of(9, 0));
        Ligne ligne = new Ligne("L1", stations, tempsDeparts);
        assertNotNull(ligne);
        assertEquals("L1", ligne.getNomLigne());
        assertEquals(stations, ligne.getStations());
        assertEquals(tempsDeparts, ligne.getTempsDeparts());
    }
    @Test
    public void testGetNomLigne() {
        Ligne ligne = new Ligne("L1", new ArrayList<>(), new ArrayList<>());
        assertEquals("L1", ligne.getNomLigne());
    }

    @Test
    public void testSetNomLigne() {
        Ligne ligne = new Ligne("L1", new ArrayList<>(), new ArrayList<>());
        ligne.setNomLigne("L2");
        assertEquals("L2", ligne.getNomLigne());
    }

    @Test
    public void testGetStations() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station("S1",null,new ArrayList<>()));
        stations.add(new Station("S2",null,new ArrayList<>()));
        Ligne ligne = new Ligne("L1", stations, new ArrayList<>());
        assertEquals(stations, ligne.getStations());
    }

    @Test
    public void testSetStations() {
        List<Station> stations1 = new ArrayList<>();
        stations1.add(new Station("S1",null,new ArrayList<>()));
        stations1.add(new Station("S2",null,new ArrayList<>()));
        Ligne ligne = new Ligne("L1", stations1, new ArrayList<>());

        List<Station> stations2 = new ArrayList<>();
        stations2.add(new Station("S3",null,new ArrayList<>()));
        stations2.add(new Station("S4",null,new ArrayList<>()));

        ligne.setStations(stations2);
        assertEquals(stations2, ligne.getStations());
    }

    @Test
    public void testGetTempsDeparts() {
        List<LocalTime> tempsDeparts = new ArrayList<>();
        tempsDeparts.add(LocalTime.of(8, 0));
        tempsDeparts.add(LocalTime.of(9, 0));
        Ligne ligne = new Ligne("L1", new ArrayList<>(), tempsDeparts);
        assertEquals(tempsDeparts, ligne.getTempsDeparts());
    }

    @Test
    public void testSetTempsDeparts() {
        List<LocalTime> tempsDeparts1 = new ArrayList<>();
        tempsDeparts1.add(LocalTime.of(8, 0));
        tempsDeparts1.add(LocalTime.of(9, 0));
        Ligne ligne = new Ligne("L1", new ArrayList<>(), tempsDeparts1);

        List<LocalTime> tempsDeparts2 = new ArrayList<>();
        tempsDeparts2.add(LocalTime.of(10, 0));
        tempsDeparts2.add(LocalTime.of(11, 0));

        ligne.setTempsDeparts(tempsDeparts2);
        assertEquals(tempsDeparts2, ligne.getTempsDeparts());
    }
}
