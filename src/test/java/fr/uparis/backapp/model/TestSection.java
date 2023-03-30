package fr.uparis.backapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Testeur de la classe Station
 */
public class TestSection {
    private Section section;
    final private Station station1 = new Station("Station 1", new Coordonnee(0, 0));
    final private Station station2 = new Station("Station 2", new Coordonnee(1, 1));
    final private LocalTime duree = LocalTime.of(0, 5);
    final private double distance = 1.0;
    final private Ligne ligne = new Ligne("Ligne A");

    /**
     * Réinitialisation des attributs avant tous les tests.
     */
    @BeforeEach
    public void setUp() {
        section = new Section(station1, station2, duree, distance, ligne);
    }

    /**
     * Teste le constructeur de Section.
     */
    @Test
    public void testConstructor() {
        assertNotNull(section);
        assertThrows(IllegalArgumentException.class, () -> new Section(station1, station1, duree, distance, ligne));
    }

    /**
     * Teste le Getter sur la station de départ.
     */
    @Test
    public void testGetStationDepart() {
        assertEquals(new Station("Station 1", new Coordonnee(0, 0)), section.getStationDepart());
    }

    /**
     * Teste le Getter sur la station d'arrivée.
     */
    @Test
    public void testGetStationArrivee() {
        assertEquals(new Station("Station 2", new Coordonnee(1, 1)), section.getStationArrivee());
    }

    /**
     * Teste le Getter sur la durée d'une Section.
     */
    @Test
    public void testGetDuree() {
        assertEquals(LocalTime.of(0, 5), section.getDuree());
    }

    /**
     * Teste le Getter sur la distance d'une Section.
     */
    @Test
    public void testGetDistance() {
        assertEquals(1.0, section.getDistance());
    }

    /**
     * Teste le Getter de la ligne d'une Section.
     */
    @Test
    public void testGetLigne(){
        assertEquals(new Ligne("Ligne A"), section.getLigne());
    }

    /**
     * Tests sur les horaires : get, ajout et suppression.
     */
    @Test
    public void testsHoraires() {
        LocalTime horaire1 = LocalTime.now();
        LocalTime horaire2 = LocalTime.of(0, 5);

        assertEquals(0, section.getHorairesDepart().size());

        section.removeHoraireDepart(horaire1);
        assertEquals(0, section.getHorairesDepart().size());

        section.addHoraireDepart(horaire1);
        section.addHoraireDepart(horaire1);
        section.addHoraireDepart(horaire2);
        assertEquals(2, section.getHorairesDepart().size());

        section.removeHoraireDepart(horaire1);
        assertEquals(1, section.getHorairesDepart().size());
        assertFalse(section.getHorairesDepart().contains(horaire1));
        assertTrue(section.getHorairesDepart().contains(horaire2));
    }

    /**
     * Tests d'égalité.
     */
    @Test
    public void testsEquals() {
        Section section1 = new Section(station1, station2, duree, distance, ligne);
        assertEquals(section, section1);
        assertEquals(section.hashCode(), section1.hashCode());

        Section section2 = new Section(station2, station1, duree, distance, ligne);
        assertNotEquals(section, section2);
        assertNotEquals(section.hashCode(), section2.hashCode());
    }

    /**
     * Teste l'affichage d'une Section.
     */
    @Test
    public void testsToString() {
        assertEquals("Ligne A : Station 1 -> Station 2 (durée = 00:05, distance = 1.0 km)", section.toString());

        LocalTime horaire = LocalTime.of(15, 0);
        section.addHoraireDepart(horaire);
        assertEquals("Ligne A : Station 1 -> Station 2 (durée = 00:05, distance = 1.0 km)\n    15:00", section.toString());
    }

    /**
     * Teste si une Section va d'une station à une autre.
     */
    @Test
    public void testIsGoingFromTo() {
        assertTrue(section.isGoingFromTo(station1, station2));

        Station station3 = new Station("Station 3", new Coordonnee(0, 0));
        assertFalse(section.isGoingFromTo(station1, station3));
        assertFalse(section.isGoingFromTo(station3, station2));
    }
}