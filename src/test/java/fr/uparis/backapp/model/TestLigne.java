package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testeur de la classe Ligne
 */
public class TestLigne {

    /**
     * Teste la construction de Ligne avec des parametres null
     */
    @Test
    public void testConstructorWithNullValues() {
        Ligne ligne = new Ligne(null, null, null);
        assertNotNull(ligne);
        assertEquals(null, ligne.getNomLigne());
        assertEquals(null, ligne.getStations());
        assertEquals(null, ligne.getTempsDeparts());
    }

    /**
     * Teste la construction de Ligne avec des listes vides
     */
    @Test
    public void testConstructorWithEmptyValues() {
        Set<Station> stations = new LinkedHashSet<>();
        List<LocalTime> tempsDeparts = new ArrayList<>();
        Ligne ligne = new Ligne("", stations, tempsDeparts);
        assertNotNull(ligne);
        assertEquals("", ligne.getNomLigne());
        assertEquals(stations, ligne.getStations());
        assertEquals(tempsDeparts, ligne.getTempsDeparts());
    }

    /**
     * Teste la construction de Ligne avec des parametres corrects
     */
    @Test
    public void testConstructorWithNonEmptyValues() {
        Set<Station> stations =Set.of(new Station("S1",null,new LinkedHashSet<>()), new Station("S2",null,new LinkedHashSet<>()));
        List<LocalTime> tempsDeparts = Arrays.asList(LocalTime.of(8, 0), LocalTime.of(9, 0));
        Ligne ligne = new Ligne("L1", stations, tempsDeparts);
        assertNotNull(ligne);
        assertEquals("L1", ligne.getNomLigne());
        assertEquals(stations, ligne.getStations());
        assertEquals(tempsDeparts, ligne.getTempsDeparts());
    }

    /**
     * Teste le Getter de nomLigne
     */
    @Test
    public void testGetNomLigne() {
        Ligne ligne = new Ligne("L1", new LinkedHashSet<>() , new ArrayList<>());
        assertEquals("L1", ligne.getNomLigne());
    }

    /**
     * Teste le Setter de nomLigne
     */
    @Test
    public void testSetNomLigne() {
        Ligne ligne = new Ligne("L1", new LinkedHashSet<>(), new ArrayList<>());
        ligne.setNomLigne("L2");
        assertEquals("L2", ligne.getNomLigne());
    }

    /**
     * Teste le Getter de la liste des Station
     */
    @Test
    public void testGetStations() {
        Set<Station> stations = new LinkedHashSet<>();
        stations.add(new Station("S1",new Coordonnee(1,2),new LinkedHashSet<>()));
        stations.add(new Station("S2",new Coordonnee(2,2),new LinkedHashSet<>()));
        Ligne ligne = new Ligne("L1", stations, new ArrayList<>());
        assertEquals(stations, ligne.getStations());
    }

    /**
     * Teste le Setter de la liste des Station
     */
    @Test
    public void testSetStations() {
        Set<Station> stations1 = new LinkedHashSet<>();
        stations1.add(new Station("S1",new Coordonnee(1,2),new LinkedHashSet<>()));
        stations1.add(new Station("S2",new Coordonnee(2,2),new LinkedHashSet<>()));
        Ligne ligne = new Ligne("L1", stations1, new ArrayList<>());

        Set<Station> stations2 = new LinkedHashSet<>();
        stations2.add(new Station("S3",new Coordonnee(1,2),new LinkedHashSet<>()));
        stations2.add(new Station("S4",new Coordonnee(2,2),new LinkedHashSet<>()));

        ligne.setStations(stations2);
        assertEquals(stations2, ligne.getStations());
    }

    /**
     * Teste le Getter de la liste des horaires de departs
     */
    @Test
    public void testGetTempsDeparts() {
        List<LocalTime> tempsDeparts = new ArrayList<>();
        tempsDeparts.add(LocalTime.of(8, 0));
        tempsDeparts.add(LocalTime.of(9, 0));
        Ligne ligne = new Ligne("L1", new LinkedHashSet<>(), tempsDeparts);
        assertEquals(tempsDeparts, ligne.getTempsDeparts());
    }

    /**
     * Teste le Setter de la liste des horaires de departs
     */
    @Test
    public void testSetTempsDeparts() {
        List<LocalTime> tempsDeparts1 = new ArrayList<>();
        tempsDeparts1.add(LocalTime.of(8, 0));
        tempsDeparts1.add(LocalTime.of(9, 0));
        Ligne ligne = new Ligne("L1", new LinkedHashSet<>(), tempsDeparts1);

        List<LocalTime> tempsDeparts2 = new ArrayList<>();
        tempsDeparts2.add(LocalTime.of(10, 0));
        tempsDeparts2.add(LocalTime.of(11, 0));

        ligne.setTempsDeparts(tempsDeparts2);
        assertEquals(tempsDeparts2, ligne.getTempsDeparts());
    }
}
