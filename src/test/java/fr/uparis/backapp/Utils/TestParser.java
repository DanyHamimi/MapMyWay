package fr.uparis.backapp.Utils;

import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Ligne;
import fr.uparis.backapp.model.Section;
import fr.uparis.backapp.model.Station;
import org.junit.jupiter.api.Test;

import java.util.List;
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
     * Teste le getter de stations et le parsage de son format de données.
     */
    @Test
    void testGetSectionsAndCorrectSection() {
        Set<Section> sections = parser.getSections();
        assertEquals(NB_SECTIONS, sections.size()); //1770 lignes dans le csv

        Optional<Section> section = sections.stream()
                .filter(s -> s.isGoingFromTo(new Station("Buttes Chaumont", new Coordonnee(2.381569842088008, 48.87849908839857)),
                        new Station("Bolivar", new Coordonnee(2.374152140698752, 48.880789805531926)))
                        && s.getLigne().getNomLigne().equals("7B variant 2"))
                .findFirst();
        assertTrue(section.isPresent());
        assertEquals(1.698589040958272, section.get().getDistance());
        assertEquals("PT43S", section.get().getDuree().toString());
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
                case "Buttes Chaumont":
                    assertEquals(4, station.getCorrespondances().size());
                    nbValides++;
                    break;
                case "Bolivar":
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

    /**
     * Teste la lecture des horaires du réseau.
     */
    @Test
    public void testLectTime() {
        //TODO
        parser.lect_Time();
    }
}
