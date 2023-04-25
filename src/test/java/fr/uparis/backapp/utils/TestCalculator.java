package fr.uparis.backapp.utils;

import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Reseau;
import fr.uparis.backapp.model.lieu.Station;
import fr.uparis.backapp.model.section.Section;
import fr.uparis.backapp.utils.constants.Constants;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testeur des calculs d'itinéraires.
 */
public class TestCalculator {
    Reseau reseau = Reseau.getInstance();

    /**
     * Teste le calcul d'itinéraire à pied.
     */
    @Test
    void testsWalkingItineraire() {
        LocalTime horaireDepart = LocalTime.of(23, 58, 59, 0);
        Coordonnee depart = reseau.getStation("Danube").getLocalisation();
        Coordonnee arrivee1 = reseau.getStation("Botzaris").getLocalisation();
        Coordonnee arrivee2 = reseau.getStation("Buttes Chaumont").getLocalisation();
        Coordonnee arrivee3 = reseau.getStation("Stalingrad").getLocalisation();

        Calculator.changeAPied();
        List<Section[]> trajetsTrouves1 = Calculator.itineraireFactory(depart, arrivee1, horaireDepart);
        assertNotNull(trajetsTrouves1);
        assertEquals(1, trajetsTrouves1.size());
        assertEquals(1, trajetsTrouves1.get(0).length);

        Calculator.changeAPied();
        List<Section[]> trajetsTrouves2 = Calculator.itineraireFactory(depart, arrivee2, horaireDepart);
        assertEquals(1, trajetsTrouves2.size());
        assertEquals(1, trajetsTrouves2.get(0).length);

        Calculator.changeAPied();
        List<Section[]> trajetsTrouves3 = Calculator.itineraireFactory(depart, arrivee3, horaireDepart);
        assertEquals(1, trajetsTrouves3.size());
        assertEquals(1, trajetsTrouves3.get(0).length);
    }

    /**
     * Teste l'égalité des trajets en mode sportif.
     */
    @Test
    public void testsSameSportifItineraire(){
        LocalTime horaireDepart = LocalTime.of(12, 28, 59, 0);
        Coordonnee depart = reseau.getStation("Nation").getLocalisation();
        Coordonnee arrivee = reseau.getStation("Boucicaut").getLocalisation();

        //Trajets avec au moins 10 minutes de marche
        Calculator.changeMarcherAuMoinsDistance(Constants.DEFAULT_ECART_DISTANCE);
        List<Section[]> trajetsTrouves0 = Calculator.itineraireFactory(depart, arrivee, horaireDepart);
        assertNotNull(trajetsTrouves0);
        assertTrue(trajetsTrouves0.size() <= Constants.MAX_TRAJETS_NUMBER);

        Calculator.changeMarcherAuMoinsTemps(Duration.ofMinutes(10));
        List<Section[]> trajetsTrouves1 = Calculator.itineraireFactory(depart, arrivee, horaireDepart);
        assertNotNull(trajetsTrouves1);
        assertEquals(trajetsTrouves0.size(), trajetsTrouves1.size());

        Section[] trajet0, trajet1;
        for(int i = 0; i < trajetsTrouves1.size(); i++) {
            trajet0 = trajetsTrouves0.get(i);
            trajet1 = trajetsTrouves1.get(i);
            assertEquals(trajet0.length, trajet1.length);
            assertEquals(trajet0[trajet0.length - 1].getArrivee().getHoraireDePassage(),
                    trajet1[trajet1.length - 1].getArrivee().getHoraireDePassage());
        }
    }

    /**
     * Teste le calcul d'itinéraire, qui donne des trajets en mode sportif, en vérifiant les minimums parcourus.
     */
    @Test
    public void testsItineraireModeSportif() {
        LocalTime horaireDepart = LocalTime.of(12, 28, 59, 0);
        Station depart = reseau.getStation("Lourmel");
        Station arrivee1 = reseau.getStation("Boucicaut");
        Station arrivee2 = reseau.getStation("Mairie d'Issy");

        Calculator.changeMarcherAuMoinsDistance(0.47);
        List<Section[]> trajetsTrouves1 = Calculator.itineraireFactory(depart.getLocalisation(), arrivee1.getLocalisation(), horaireDepart);
        assertNotNull(trajetsTrouves1);
        assertTrue(trajetsTrouves1.size() <= Constants.MAX_TRAJETS_NUMBER);
        assertNotEquals(1, trajetsTrouves1.get(0).length); //Pied ; Lourmel ; Boucicaut ; Félix Faure ; Pied

        Calculator.changeMarcherAuMoinsDistance(0.475);
        List<Section[]> trajetsTrouves0 = Calculator.itineraireFactory(depart.getLocalisation(), arrivee1.getLocalisation(), horaireDepart);
        assertNotNull(trajetsTrouves0);
        assertTrue(trajetsTrouves0.size() <= Constants.MAX_TRAJETS_NUMBER);
        assertEquals(1, trajetsTrouves0.get(0).length); //Pied


        Calculator.changeMarcherAuMoinsDistance(0.91);
        List<Section[]> trajetsTrouves2 = Calculator.itineraireFactory(depart.getLocalisation(), arrivee2.getLocalisation(), horaireDepart);
        assertNotNull(trajetsTrouves2);
        assertTrue(trajetsTrouves2.size() <= Constants.MAX_TRAJETS_NUMBER);
        assertEquals(4, trajetsTrouves2.get(0).length); //Pied ; Porte de Versailles ; Corentin Celton ; Mairie d'Issy ; Pied

        Calculator.changeMarcherAuMoinsDistance(0.919);
        List<Section[]> trajetsTrouves4 = Calculator.itineraireFactory(depart.getLocalisation(), arrivee2.getLocalisation(), horaireDepart);
        assertNotNull(trajetsTrouves4);
        assertTrue(trajetsTrouves4.size() <= Constants.MAX_TRAJETS_NUMBER);
        assertEquals(3, trajetsTrouves4.get(0).length); //Pied ; Corentin Celton ; Mairie d'Issy ; Pied
    }

    /**
     * Teste l'égalité des trajets en mode paresseux, avec le mode sportif dans les cas extrêmes.
     */
    @Test
    public void testsSameLazyItineraire() {
        LocalTime horaireDepart = LocalTime.of(12, 28, 59, 0);
        Coordonnee depart = reseau.getStation("Nation").getLocalisation();
        Coordonnee arrivee = reseau.getStation("Boucicaut").getLocalisation();

        //Trajet par défaut
        List<Section[]> trajetsTrouves0 = Calculator.itineraireFactory(depart, arrivee, horaireDepart);
        assertNotNull(trajetsTrouves0);
        assertTrue(trajetsTrouves0.size() <= Constants.MAX_TRAJETS_NUMBER);

        //Trajet avec au plus 0 minute de marche entre les stations
        Calculator.changeMarcherAuPlus(Constants.DEFAULT_MIN_DISTANCE);
        List<Section[]> trajetsTrouves1 = Calculator.itineraireFactory(depart, arrivee, horaireDepart);
        assertNotNull(trajetsTrouves1);
        assertEquals(trajetsTrouves0.size(), trajetsTrouves1.size());

        //Trajet avec au moins 0 minute de marche
        Calculator.changeMarcherAuMoinsDistance(Constants.DEFAULT_MIN_DISTANCE);
        List<Section[]> trajetsTrouves2 = Calculator.itineraireFactory(depart, arrivee, horaireDepart);
        assertNotNull(trajetsTrouves2);
        assertEquals(trajetsTrouves0.size(), trajetsTrouves2.size());

        Section[] trajet0, trajet1, trajet2;
        for(int i = 0; i < trajetsTrouves1.size(); i++) {
            trajet0 = trajetsTrouves0.get(i);
            trajet1 = trajetsTrouves1.get(i);
            trajet2 = trajetsTrouves2.get(i);

            assertEquals(trajet0.length, trajet1.length);
            assertEquals(trajet0.length, trajet2.length);

            assertEquals(trajet0[trajet0.length - 1].getArrivee().getHoraireDePassage(),
                    trajet1[trajet1.length - 1].getArrivee().getHoraireDePassage());
            assertEquals(trajet0[trajet0.length - 1].getArrivee().getHoraireDePassage(),
                    trajet1[trajet2.length - 1].getArrivee().getHoraireDePassage());
        }
    }

    /**
     * Teste des trajets qui ne renvoient pas d'itinéraire avec dijkstra à cause de l'horaire des trains.
     */
    @Test
    public void testsNoItineraire(){
        LocalTime horaireDepart = LocalTime.of(23, 58, 59, 0);
        Coordonnee depart = reseau.getStation("Lourmel").getLocalisation();
        Coordonnee arrivee = reseau.getStation("Boucicaut").getLocalisation();

        //Trajet par défaut
        List<Section[]> trajetsTrouves0 = Calculator.itineraireFactory(depart, arrivee, horaireDepart);
        assertNotNull(trajetsTrouves0);
        assertEquals(1, trajetsTrouves0.size());

        //Trajet lazy
        Calculator.changeMarcherAuPlus(Constants.DEFAULT_MIN_DISTANCE);
        List<Section[]> trajetsTrouves1 = Calculator.itineraireFactory(depart, arrivee, horaireDepart);
        assertNotNull(trajetsTrouves1);
        assertEquals(1, trajetsTrouves1.size());

        //Trajet sportif
        Calculator.changeMarcherAuMoinsDistance(1.0);
        List<Section[]> trajetsTrouves2 = Calculator.itineraireFactory(depart, arrivee, horaireDepart);
        assertNotNull(trajetsTrouves2);
        assertEquals(1, trajetsTrouves2.size());
    }
}