package fr.uparis.backapp.Utils;

import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Reseau;
import fr.uparis.backapp.model.Station;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testeur des calculs d'itinéraires
 */
public class TestCalculator {
    Reseau reseau = Reseau.getInstance();

    /**
     * Teste du calcul d'itinéraire.
     */
    @Test
    public void testIteneraire() {
        Coordonnee coordonnee = new Coordonnee(2.289435418542214, 48.87566737659971);
        Calculator.itineraire(coordonnee, coordonnee);
    }

    /**
     *
     */
    @Test
    public void testPlan0() {
    }

    /**
     *
     */
    @Test
    public void testPlan1() {
    }

    /**
     *
     */
    @Test
    public void testPlan2() {
    }

    /**
     *
     */
    @Test
    public void testPlan3() {
    }

    /**
     * Teste si les stations proches obtenues sont celles attendues.
     */
    @Test
    public void testGetNearStations() {
        Coordonnee coordonnee = new Coordonnee(2.289435418542214, 48.87566737659971); //coordonnée d'une station
        double maxDistance = 0.1; //100 m
        double minDistance = 0.0; //0 m
        List<Station> near1 = Calculator.getNearStations(coordonnee, maxDistance, minDistance);
        assertEquals(1, near1.size()); //la station elle-même

        Station station = new Station("station test", coordonnee);
        reseau.addStation(station);
        List<Station> near2 = Calculator.getNearStations(coordonnee, maxDistance, minDistance);
        assertEquals(2, near2.size());

        reseau.removeStation(station);
        List<Station> near3 = Calculator.getNearStations(coordonnee, maxDistance, minDistance);
        assertEquals(1, near3.size());

        List<Station> near4 = Calculator.getNearStations(coordonnee, -1.0, -1.0);
        assertTrue(near4.size()>1);
    }
}