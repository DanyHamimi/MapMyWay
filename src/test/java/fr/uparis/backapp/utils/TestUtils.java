package fr.uparis.backapp.utils;

import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Ligne;
import fr.uparis.backapp.model.Section;
import fr.uparis.backapp.model.Station;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static fr.uparis.backapp.utils.Utils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
    /**
     * Teste la conversion de durée en dizaine de secondes vers Duration.
     */
    @Test
    public void testCorrectDuration() {
        String duration1 = "10:02";
        assertEquals("PT1M41S", correctDuration(duration1).toString());

        String duration2 = "02:35";
        assertEquals("PT24S", correctDuration(duration2).toString());

        String duration3 = "12:45";
        assertEquals("PT2M5S", correctDuration(duration3).toString());
    }

    /**
     * Teste la conversion d'une distance en dizaine de km vers km.
     */
    @Test
    public void testCorrectDistance() {
        String distance1 = "9.552567634725916";
        assertEquals(Double.parseDouble("0.955"), correctDistance(distance1));

        String distance2 = "16.242977461710105";
        assertEquals(Double.parseDouble("1.624"), correctDistance(distance2));

        String distance3 = "27.99616623787354";
        assertEquals(Double.parseDouble("2.8"), correctDistance(distance3));
    }

    /**
     * Teste la recherche de sections avec la station de début et le nom de la ligne, dans un ensemble de sections donné.
     */
    @Test
    public void testFindSectionDepart() {
        Station station1 = new Station("station1", new Coordonnee("1,0"));
        Station station2 = new Station("station2", new Coordonnee("1,0"));
        Station station3 = new Station("station3", new Coordonnee("1,0"));

        Duration duree = Duration.ofMinutes(2).plusSeconds(5);
        double distance = 5.054;
        Ligne ligne = new Ligne("ligne");

        Section section1 = new Section(station1, station2, duree, distance, ligne);
        Section section2 = new Section(station2, station3, duree, distance, ligne);

        Set<Section> sectionSet = new HashSet<>();
        sectionSet.add(section1);
        sectionSet.add(section2);

        assertEquals(section1, findSectionDepart(sectionSet, station1.getNomStation(), ligne.getNomLigne()));
        assertEquals(section2, findSectionDepart(sectionSet, station2.getNomStation(), ligne.getNomLigne()));
        assertEquals(null, findSectionDepart(sectionSet, station3.getNomStation(), ligne.getNomLigne()));
    }

    /**
     * Teste la conversion d'un horaire vers le format "hh:mm".
     */
    @Test
    public void testCorrectTime() {
        String time1 = "5:8";
        String expected1 = "05:08";
        assertEquals(expected1, correctTime(time1));

        String time2 = "12:45";
        String expected2 = "12:45";
        assertEquals(expected2, correctTime(time2));

        String time3 = "6:7";
        String expected3 = "06:07";
        assertEquals(expected3, correctTime(time3));
    }

    /**
     * Teste le calcul de distance entre deux coordonnées.
     */
    @Test
    void testDistanceBetween(){
        Coordonnee origine1 = new Coordonnee(52.2296756, 21.0122287);
        Coordonnee destination1 = new Coordonnee(52.2296756, 21.0122287);
        assertEquals(0.000, distanceBetween(origine1, destination1));

        Coordonnee origine2 = new Coordonnee(48.948559, 2.063739);
        Coordonnee destination2 = new Coordonnee(48.94796491807184, 2.0651253924818516);
        assertEquals(0.121, distanceBetween(origine2, destination2)); //résultat depuis Google Maps

        Coordonnee origine3 = new Coordonnee(52.2296756, 21.0122287);
        Coordonnee destination3 = new Coordonnee(52.406374, 16.9251681);
        assertEquals(278.537, distanceBetween(origine3, destination3)); //résultat depuis un site de calcul
    }
}
