package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Testeur de la classe Reseau
 */
public class TestReseau {
    final private Reseau reseau = Reseau.getInstance();
    final private int NB_STATIONS = 712; //tri sur excel par nom de station puis par coordonnée
                                         //et comparaison avec la formule =IF(AND(A2=A1;B2=B1);C1;C1+1)
    final private int NB_SECTIONS = 1770; //nombre de lignes du fichier excel fourni

    /**
     * Teste le Getter de l'instance de Reseau.
     */
    @Test
    void testGetInstance() {
        Reseau reseau1 = Reseau.getInstance();
        assertSame(reseau, reseau1);
    }

    /**
     * Teste sur les Station Station de Reseau : get, ajout et suppression.
     */
    @Test
    void testsStations() {
        Set<Station> stations = reseau.getStations();
        assertEquals(NB_STATIONS, stations.size());

        //cas d'ajout et suppression simples
        Station station = new Station("station", new Coordonnee(1, 0));

        reseau.removeStation(station);
        assertEquals(NB_STATIONS, stations.size());

        reseau.addStation(station);
        reseau.addStation(station); //doublon
        assertEquals(NB_STATIONS + 1, stations.size());

        reseau.removeStation(station);
        assertEquals(NB_STATIONS, stations.size());

        //Cas où supprimer une section supprime aussi les stations
        Station station1 = new Station("station 1", new Coordonnee(1, 0));
        Station station2 = new Station("station 2", new Coordonnee(1, 0));
        Section section = new Section(station1, station2, LocalTime.of(4, 0), 1.0, new Ligne("ligne"));
        reseau.addSection(section);
        assertEquals(NB_STATIONS + 2, stations.size());

        reseau.removeStation(station1);
        assertEquals(NB_STATIONS, stations.size());
    }

    /**
     * Teste sur les Section de Reseau : get, ajout et suppression.
     */
    @Test
    void testsSections() {
        Set<Section> sections = reseau.getSections();
        assertEquals(NB_SECTIONS, sections.size());

        Station station1 = new Station("station 1", new Coordonnee(1, 0));
        Station station2 = new Station("station 2", new Coordonnee(1, 0));
        Section section = new Section(station1, station2, LocalTime.of(4, 0), 1.0, new Ligne("ligne"));

        reseau.removeSection(section);
        assertEquals(NB_SECTIONS, sections.size());

        reseau.addSection(section);
        reseau.addSection(section); //doublon
        assertEquals(NB_SECTIONS + 1, sections.size());
        assertEquals(NB_STATIONS + 2, reseau.getStations().size());

        reseau.removeSection(section);
        assertEquals(NB_SECTIONS, sections.size());
        assertEquals(NB_STATIONS, reseau.getStations().size());
    }

    /**
     * Teste le calcul de distance entre deux coordonnées.
     */
    /*@Test
    void testDistanceBetween(){
        Coordonnee origine1 = new Coordonnee(52.2296756, 21.0122287);
        Coordonnee destination1 = new Coordonnee(52.2296756, 21.0122287);
        assertEquals(0.000, Reseau.distanceBetween(origine1, destination1));

        Coordonnee origine2 = new Coordonnee(48.948559, 2.063739);
        Coordonnee destination2 = new Coordonnee(48.94796491807184, 2.0651253924818516);
        assertEquals(0.121, Reseau.distanceBetween(origine2, destination2)); //résultat depuis Google Maps

        Coordonnee origine3 = new Coordonnee(52.2296756, 21.0122287);
        Coordonnee destination3 = new Coordonnee(52.406374, 16.9251681);
        assertEquals(278.537, Reseau.distanceBetween(origine3, destination3)); //résultat depuis un site de calcul
    }*/

    /**
     * Teste si les stations proches obtenues sont celles attendues.
     */
    @Test
    void testsGetNearStations() {
        Coordonnee coordonnee = new Coordonnee(2.289435418542214, 48.87566737659971); //coordonnée d'une station
        double maxDistance = 0.1; //100 m
        double minDistance = 0.0; //0 m
        List<Station> near1 = reseau.getNearStations(coordonnee, maxDistance, minDistance);
        assertEquals(1, near1.size()); //la station elle-même

        Station station = new Station("station test", coordonnee);
        reseau.addStation(station);
        List<Station> near2 = reseau.getNearStations(coordonnee, maxDistance, minDistance);
        assertEquals(2, near2.size());

        reseau.removeStation(station);
        List<Station> near3 = reseau.getNearStations(coordonnee, maxDistance, minDistance);
        assertEquals(1, near3.size());
    }

    /**
     * Teste le calcul des plus courts chemins du Reseau
     */
    @Test
    void testDjikstra() {
        //TODO write tests
    }
}