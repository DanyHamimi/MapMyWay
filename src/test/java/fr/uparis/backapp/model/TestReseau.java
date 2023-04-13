package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testeur de la classe Reseau
 */
public class TestReseau {
    final private Reseau reseau = Reseau.getInstance();
    final private int NB_STATIONS = 308; //tri sur excel par nom de station (sans prise en compte des coordonnées)
                                         //et comparaison avec la formule =IF(A2=A1;C1;C1+1)
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
        assertEquals(null, reseau.getStation("station"));
        assertEquals(null, reseau.getStation(new Coordonnee(1, 0)));

        reseau.addStation(station);
        reseau.addStation(station); //doublon
        assertEquals(NB_STATIONS + 1, stations.size());
        assertEquals(station, reseau.getStation("station"));
        assertEquals(station, reseau.getStation(new Coordonnee(1, 0)));

        reseau.removeStation(station);
        assertEquals(NB_STATIONS, stations.size());


        //Cas où supprimer une station supprime aussi sections et stations en cascade
        Station station1 = new Station("station 1", new Coordonnee(1, 0));
        Station station2 = new Station("station 2", new Coordonnee(1, 0));
        Section section = new Section(station1, station2, Duration.of(5, ChronoUnit.SECONDS), 1.0, new Ligne("ligne"));
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
        Section section = new Section(station1, station2, Duration.of(5, ChronoUnit.SECONDS), 1.0, new Ligne("ligne"));

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
     * Teste le calcul du plus court chemin avec dijkstra, qui donne un résultat.
     */
    @Test
    void testDijkstraSuccessful(){
        LocalTime horaireDepart = LocalTime.of(12, 28, 59, 0);
        Station origine = reseau.getStation("Lourmel");
        Station destination = reseau.getStation("Boucicaut");
        Station destination2 = reseau.getStation("Félix Faure");
        Station destination3 = reseau.getStation("Bir-Hakeim");

        List<Section> trajetTrouve1 = reseau.djikstra(origine, destination, horaireDepart);
        assertNotNull(trajetTrouve1);
        assertEquals(1, trajetTrouve1.size()); //Lourmel ; Boucicaut

        List<Section> trajetTrouve2 = reseau.djikstra(origine, destination2, horaireDepart);
        assertNotNull(trajetTrouve2);
        assertEquals(2, trajetTrouve2.size()); //Lourmel ; Boucicaut ; Félix Faure

        List<Section> trajetTrouve3 = reseau.djikstra(origine, destination3, horaireDepart);
        assertNotNull(trajetTrouve3);
        assertEquals(6, trajetTrouve3.size()); //Lourmel ; Boucicaut ; Félix Faure ; Commerce ; La Motte-Picquet ; Dupleix ; Bir-Hakeim
    }

    /**
     * Teste le calcul du plus court chemin avec dijkstra, qui ne donne pas de résultat.
     */
    @Test
    void testDijkstraFailed(){
        LocalTime horaireDepart = LocalTime.of(23, 58, 59, 0);
        Station origine = reseau.getStation("Danube");
        Station destination = reseau.getStation("Botzaris");
        Station destination2 = reseau.getStation("Buttes Chaumont");
        Station destination3 = reseau.getStation("Stalingrad");

        List<Section> trajetTrouve = reseau.djikstra(origine, destination, horaireDepart);
        assertNull(trajetTrouve);

        List<Section> trajetTrouve2 = reseau.djikstra(origine, destination2, horaireDepart);
        assertNull(trajetTrouve2);

        List<Section> trajetTrouve3 = reseau.djikstra(origine, destination3, horaireDepart);
        assertNull(trajetTrouve3);
    }
}