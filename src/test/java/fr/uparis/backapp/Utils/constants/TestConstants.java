package fr.uparis.backapp.Utils.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testeur de la classe Constants
 */
public class TestConstants{
    @Test
    public void testValues(){
        Constants constants = new Constants();
        assertEquals(";", constants.DELIMITER);
        assertEquals("map.data.file.path", constants.CSV_FILE_PATH_PROPERTY);
        assertEquals(0, constants.STATION_DEPART_INDEX);
        assertEquals(2, constants.STATION_ARRIVEE_INDEX);
        assertEquals(1, constants.STATION_DEPART_COORDONEES_INDEX);
        assertEquals(3, constants.STATION_ARRIVEE_COORDONEES_INDEX);
        assertEquals(4, constants.NOM_LIGNE_INDEX);
        assertEquals(5, constants.DUREE_INDEX);
        assertEquals(6, constants.DISTANCE_INDEX);
        assertEquals(5, constants.POS);
        assertEquals(10.0, constants.DEFAULT_MAX_DISTANCE);
        assertEquals(0.0, constants.DEFAULT_MIN_DISTANCE);
    }
}