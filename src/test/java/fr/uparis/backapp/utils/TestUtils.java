package fr.uparis.backapp.utils;

import fr.uparis.backapp.model.Coordonnee;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static fr.uparis.backapp.utils.Utils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testeur de la classe Utils.
 */
public class TestUtils {
    /**
     * Teste l'arrondi à une précision de 3 chiffres après la virgule.
     */
    @Test
    public void tests3Precision() {
        assertEquals(3.333, truncateDoubleTo3Precision(3.333));
        assertEquals(3.333, truncateDoubleTo3Precision(3.33333));
        assertEquals(3.333, truncateDoubleTo3Precision(3.3334));
        assertEquals(3.334, truncateDoubleTo3Precision(3.33351));
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
    void testDistanceBetween() {
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

    /**
     * Teste la durée de marche d'une certaine distance en km.
     */
    @Test
    void testWalkingDuration() {
        assertEquals(Duration.ofSeconds(0), walkingDurationOf(0));
        assertEquals(Duration.ofHours(1), walkingDurationOf(5.0));
        assertEquals(Duration.ofMinutes(30), walkingDurationOf(2.5));
        assertEquals(Duration.ofMinutes(6), walkingDurationOf(0.5));
        assertEquals(Duration.ofMinutes(3), walkingDurationOf(0.25));
        assertEquals(Duration.ofMinutes(1).plusSeconds(12), walkingDurationOf(0.1));
    }
}
