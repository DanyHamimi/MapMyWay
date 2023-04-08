package fr.uparis.backapp.utils;

import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Ligne;
import fr.uparis.backapp.model.Section;
import fr.uparis.backapp.model.Station;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testeur des Parser du réseau et des horaires
 */
public class TestParser {
    final private Parser parser = Parser.getInstance();
    final private int NB_LIGNES = 93; //tri sur excel sur le nom de la ligne
                                      //et comparaison avec la formule =IF(AND(A2=A1;B2=B1);C1;C1+1)
    final private int NB_SECTIONS = 1770; //nombre de lignes du fichier excel fourni
    final private int NB_STATIONS = 308;  //tri sur excel par nom de station (sans prise en compte des coordonnées)
                                          //et comparaison avec la formule =IF(A2=A1;C1;C1+1)

    /**
     * Teste l'instanciation du parseur, qui donne le même et unique Parser pour chaque appel.
     */
    @Test
    void testGetInstance() {
        Parser parser1 = Parser.getInstance();
        assertSame(parser, parser1);
    }

    /**
     * Teste le getter de lignes.
     */
    @Test
    void testGetLignes() {
        Ligne[] lignes = parser.getLignes();
        assertEquals(NB_LIGNES, lignes.length);

        for (Ligne l : lignes)
            if (l.getNomLigne().equals("7B variant 2"))
                assertEquals(7, l.getStations().size());
    }

    /**
     * Teste le getter de sections et le parsage de son format de données.
     */
    @Test
    void testGetSectionsAndCorrectSection() {
        Set<Section> sections = parser.getSections();
        assertEquals(NB_SECTIONS, sections.size()); //1770 lignes dans le csv

        Optional<Section> section = sections.stream()
                .filter(s -> s.areStations(new Station("Buttes Chaumont", new Coordonnee("2.381569842088008, 48.87849908839857")),
                                           new Station("Bolivar", new Coordonnee("2.374152140698752, 48.880789805531926")))
                          && s.getLigne().getNomLigne().equals("7B variant 2"))
                .findFirst();
        assertTrue(section.isPresent());

        Section sectionGot = section.get();
        assertEquals(1.699, sectionGot.getDistance());
        assertEquals("PT43S", sectionGot.getDuree().toString());
    }

    /**
     * Teste les horaires de départ des sections.
     */
    @Test
    public void testHorairesDepart(){
        Set<Section> sections = parser.getSections();

        Optional<Section> section1 = sections.stream()
                .filter(s -> s.isStationDepart(new Station("Lourmel", new Coordonnee("2.2822419598550767, 48.83866086365992")))
                          && s.getLigne().getNomLigne().equals("8 variant 1"))
                .findFirst();
        Optional<Section> section2 = sections.stream()
                .filter(s -> s.isStationDepart(new Station("Boucicaut", new Coordonnee("2.2879184311245595, 48.841024160993214")))
                        && s.getLigne().getNomLigne().equals("8 variant 1"))
                .findFirst();
        Optional<Section> section3 = sections.stream()
                .filter(s -> s.isStationDepart(new Station("Lourmel", new Coordonnee("2.2822419598550767, 48.83866086365992")))
                        && s.getLigne().getNomLigne().equals("8 variant 2"))
                .findFirst();

        assertTrue(section1.isPresent());
        assertTrue(section2.isPresent());
        assertTrue(section3.isPresent());


        final int NB_HORAIRES_LOURMEL_1 = 383; //463 horaires, mais présence de doublons
        final int NB_HORAIRES_LOURMEL_2 = 373; //idem

        Section sectionLourmel1 = section1.get();
        assertEquals(NB_HORAIRES_LOURMEL_1, sectionLourmel1.getHorairesDepart().size());

        Section sectionBoucicaut = section2.get();
        assertEquals(NB_HORAIRES_LOURMEL_1, sectionBoucicaut.getHorairesDepart().size());

        Section sectionLourmel2 = section3.get();
        assertEquals(NB_HORAIRES_LOURMEL_2, sectionLourmel2.getHorairesDepart().size());
    }

    /**
     * Teste le getter de stations et le parsage des correspondances.
     */
    @Test
    void testGetStationsAndCorrectStation() {
        Station[] stations = parser.getStations();
        assertEquals(NB_STATIONS, stations.length);

        int nbValides = 0;
        for (Station station : stations) {
            switch (station.getNomStation()) {
                case "Buttes Chaumont", "Bolivar":
                    assertEquals(4, station.getCorrespondances().size());
                    nbValides++;
                    break;
                case "Villiers":
                    assertEquals(22, station.getCorrespondances().size());
                    nbValides++;
                    break;
            }
        }
        assertEquals(3, nbValides);
    }
}
