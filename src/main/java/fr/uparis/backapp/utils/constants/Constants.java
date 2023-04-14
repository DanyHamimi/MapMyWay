package fr.uparis.backapp.utils.constants;

/**
 * Classe qui rassemble les constantes du projet.
 */
public class Constants {
    /**
     * Constantes générales pour les fichiers CSV.
     */
    public static final String APPLICATION_PROPERTIES = "application.properties";
    public static final String MAP_DATA_FILE_PATH_PROPERTY = "map.data.file.path";
    public static final String SCHEDULES_FILE_PATH_PROPERTY = "schedules.file.path";
    public static final String DELIMITER = ";";

    /**
     * Index dans le fichier CSV pour le réseau.
     */
    public static final int STATION_DEPART_INDEX = 0;
    public static final int STATION_ARRIVEE_INDEX = 2;
    public static final int STATION_DEPART_COORDONEES_INDEX = 1;
    public static final int STATION_ARRIVEE_COORDONEES_INDEX = 3;
    public static final int NOM_LIGNE_INDEX = 4;
    public static final int DUREE_INDEX = 5;
    public static final int DISTANCE_INDEX = 6;

    /**
     * Index dans le fichier CSV pour les horaires.
     */
    public static final int SCHEDULES_FILE_LINE_INDEX = 0;
    public static final int SCHEDULES_FILE_TERMINUS_INDEX = 1;
    public static final int SCHEDULES_FILE_TIME_INDEX = 2;
    public static final int SCHEDULES_FILE_VARIANTE_INDEX = 3;

    /**
     * Valeurs par défaut, en km, pour trouver les stations proches d'une coordonnée.
     */
    public static final double DEFAULT_MAX_DISTANCE = 0.85;
    public static final double DEFAULT_MIN_DISTANCE = 0.0;

    /**
     * Vitesse moyenne de marche en km/h.
     */
    public static final double AVERAGE_WALKING_SPEED = 5.0;

    /**
     * Nombre maximal de trajets à renvoyer à l'utilisateur.
     */
    public static final int MAX_TRAJETS_NUMBER = 5;
}
