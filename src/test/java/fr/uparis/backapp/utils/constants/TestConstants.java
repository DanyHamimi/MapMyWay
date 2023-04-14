package fr.uparis.backapp.utils.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testeur de la classe Constants.
 */
public class TestConstants {
    @Test
    public void testValues() {
        Constants constants = new Constants();

        assertEquals("map.data.file.path", constants.MAP_DATA_FILE_PATH_PROPERTY);
        assertEquals("schedules.file.path", constants.SCHEDULES_FILE_PATH_PROPERTY);
        assertEquals(";", constants.DELIMITER);

        assertEquals(0, constants.STATION_DEPART_INDEX);
        assertEquals(2, constants.STATION_ARRIVEE_INDEX);
        assertEquals(1, constants.STATION_DEPART_COORDONEES_INDEX);
        assertEquals(3, constants.STATION_ARRIVEE_COORDONEES_INDEX);
        assertEquals(4, constants.NOM_LIGNE_INDEX);
        assertEquals(5, constants.DUREE_INDEX);
        assertEquals(6, constants.DISTANCE_INDEX);

        assertEquals(0, constants.SCHEDULES_FILE_LINE_INDEX);
        assertEquals(1, constants.SCHEDULES_FILE_TERMINUS_INDEX);
        assertEquals(2, constants.SCHEDULES_FILE_TIME_INDEX);
        assertEquals(3, constants.SCHEDULES_FILE_VARIANTE_INDEX);

        assertEquals(0.85, constants.DEFAULT_MAX_DISTANCE);
        assertEquals(0.0, constants.DEFAULT_MIN_DISTANCE);

        assertEquals(5.0, constants.AVERAGE_WALKING_SPEED);

        assertEquals(5, constants.MAX_TRAJETS_NUMBER);
    }
}