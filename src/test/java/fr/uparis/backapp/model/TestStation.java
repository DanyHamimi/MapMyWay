package fr.uparis.backapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testeur de la classe Station
 */
public class TestStation {
    private Station station;

    /**
     * Réinitialisation des attributs avant chaque test.
     * Utilisation des deux constructeurs possibles de Station.
     */
    @BeforeEach
    public void setUp() {
        station = new Station("nomStation", new Coordonnee(1, 2));
    }

    /**
     * Test sur le nom de la station.
     */
    @Test
    public void testGetNomStation() {
        assertEquals("nomStation", station.getNomStation());
    }

    /**
     * Tests sur la localisation de la station.
     */
    @Test
    public void testsGetLocalisation() {
        assertEquals(new Coordonnee(1, 2), station.getLocalisation());
    }

    /**
     * Tests sur les correspondances : get, ajout et suppression.
     */
    @Test
    public void testsCorrespondances() {
        Duration duree = Duration.of(5, ChronoUnit.SECONDS);
        double distance = 10.5;
        Ligne ligne = new Ligne("ligne");

        //sections qui vont effectivement être ajoutées
        Station station1 = new Station("station1", new Coordonnee(1.2, 1.2));
        Section section1 = new Section(station, station1, duree, distance, ligne);

        //section non ajoutée, car ne concerne pas station (station de départ différent)
        Station station2 = new Station("station2", new Coordonnee(1.2, 2.2));
        Section section2 = new Section(station1, station, duree, distance, ligne);
        Section section3 = new Section(station1, station2, duree, distance, ligne);


        assertEquals(0, station.getCorrespondances().size());

        station.removeCorrespondance(section1);
        assertEquals(0, station.getCorrespondances().size());

        station.addCorrespondance(section1);
        station.addCorrespondance(section1); //doublon qui ne va pas être ajouté
        station.addCorrespondance(section2); //station de départ ne correspond pas
        station.addCorrespondance(section3); //station de départ ne correspond pas
        assertEquals(1, station.getCorrespondances().size());
        assertTrue(station.getCorrespondances().contains(section1));

        station.removeCorrespondance(section1);
        assertEquals(0, station.getCorrespondances().size());
        assertFalse(station.getCorrespondances().contains(section1));
    }

    /**
     * Tests d'égalité.
     */
    @Test
    public void testsEquals() {
        Station station1 = new Station("nomStation", new Coordonnee(1, 2));
        assertEquals(station, station1);
        assertEquals(station.hashCode(), station1.hashCode());

        Station station2 = new Station("nomStation", new Coordonnee(1, 4));
        assertEquals(station, station2);
        assertEquals(station.hashCode(), station2.hashCode());

        Station station3 = new Station("nomStation3", new Coordonnee(1, 2));
        assertNotEquals(station, station3);
        assertNotEquals(station.hashCode(), station3.hashCode());
    }

    /**
     * Teste l'affichage d'une Station.
     */
    @Test
    public void testToString() {
        assertEquals("nomStation (latitude = 1.0 ; longitude = 2.0) -> ", station.toString());

        Station station1 = new Station("station1", new Coordonnee(1.2, 1.2));
        Duration duree = Duration.of(5, ChronoUnit.SECONDS);
        double distance = 10.5;
        Ligne ligne = new Ligne("ligne");
        Section section1 = new Section(station, station1, duree, distance, ligne);
        station.addCorrespondance(section1);

        assertEquals("nomStation (latitude = 1.0 ; longitude = 2.0) -> ligne ", station.toString());
    }
}