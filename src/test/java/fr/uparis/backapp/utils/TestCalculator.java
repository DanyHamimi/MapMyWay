package fr.uparis.backapp.utils;

import fr.uparis.backapp.model.Reseau;
import fr.uparis.backapp.model.lieu.Station;
import fr.uparis.backapp.model.section.Section;
import fr.uparis.backapp.utils.constants.Constants;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testeur des calculs d'itinéraires.
 */
public class TestCalculator {
    Reseau reseau = Reseau.getInstance();

    /**
     * Teste le calcul d'itinéraire, qui donne des trajets autres qu'à pied.
     */
    @Test
    public void testsItineraireMultipleResultat() {
        LocalTime horaireDepart = LocalTime.of(12, 28, 59, 0);
        Station origine = reseau.getStation("Lourmel");
        Station destination1 = reseau.getStation("Boucicaut");
        Station destination2 = reseau.getStation("Félix Faure");
        Station destination3 = reseau.getStation("Bir-Hakeim");

        List<Section[]> trajetsTrouves0 = Calculator.itineraire(origine, destination1, horaireDepart, 0, 0);
        assertNotNull(trajetsTrouves0);
        assertTrue(trajetsTrouves0.size() <= Constants.MAX_TRAJETS_NUMBER);
        assertEquals(3, trajetsTrouves0.get(0).length); //Pied ; Lourmel ; Boucicaut ; Pied

        List<Section[]> trajetsTrouves1 = Calculator.itineraire(origine, destination1, horaireDepart);
        assertNotNull(trajetsTrouves1);
        assertTrue(trajetsTrouves1.size() <= Constants.MAX_TRAJETS_NUMBER);
        assertEquals(3, trajetsTrouves1.get(0).length); //Pied ; Lourmel ; Boucicaut ; Pied

        List<Section[]> trajetsTrouves2 = Calculator.itineraire(origine, destination2, horaireDepart);
        assertNotNull(trajetsTrouves2);
        assertTrue(trajetsTrouves2.size() <= Constants.MAX_TRAJETS_NUMBER);
        assertEquals(4, trajetsTrouves2.get(0).length); //Pied ; Lourmel ; Boucicaut ; Félix Faure ; Pied

        List<Section[]> trajetsTrouves3 = Calculator.itineraire(origine, destination3, horaireDepart);
        assertNotNull(trajetsTrouves3);
        assertTrue(trajetsTrouves3.size() <= Constants.MAX_TRAJETS_NUMBER);
        assertEquals(8, trajetsTrouves3.get(0).length); //Pied ; Lourmel ; Boucicaut ; Félix Faure ; Commerce ; La Motte-Picquet ; Dupleix ; Bir-Hakeim ; Pied
    }

    /**
     * Teste le calcul d'itinéraire, qui ne donne qu'un trajet, celui à pied, qui est celui par défaut.
     */
    @Test
    void testsItineraireSingleResultat() {
        LocalTime horaireDepart = LocalTime.of(23, 58, 59, 0);
        Station origine = reseau.getStation("Danube");
        Station destination = reseau.getStation("Botzaris");
        Station destination2 = reseau.getStation("Buttes Chaumont");
        Station destination3 = reseau.getStation("Stalingrad");

        Section trajetAPied1 = Calculator.walkingItineraire(origine, destination, horaireDepart);
        List<Section[]> trajetsTrouves1 = Calculator.itineraire(origine, destination, horaireDepart);
        assertNotNull(trajetsTrouves1);
        assertEquals(1, trajetsTrouves1.size());
        assertEquals(1, trajetsTrouves1.get(0).length);
        assertEquals(trajetAPied1.getDuree(), trajetsTrouves1.get(0)[0].getDuree());

        Section trajetAPied2 = Calculator.walkingItineraire(origine, destination, horaireDepart);
        List<Section[]> trajetsTrouves2 = Calculator.itineraire(origine, destination2, horaireDepart);
        assertEquals(1, trajetsTrouves2.size());
        assertEquals(1, trajetsTrouves2.get(0).length);
        assertEquals(trajetAPied2.getDuree(), trajetsTrouves1.get(0)[0].getDuree());

        Section trajetAPied3 = Calculator.walkingItineraire(origine, destination, horaireDepart);
        List<Section[]> trajetsTrouves3 = Calculator.itineraire(origine, destination3, horaireDepart);
        assertEquals(1, trajetsTrouves3.size());
        assertEquals(1, trajetsTrouves3.get(0).length);
        assertEquals(trajetAPied3.getDuree(), trajetsTrouves1.get(0)[0].getDuree());
    }
}