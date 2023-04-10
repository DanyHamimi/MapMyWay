package fr.uparis.backapp.utils.constants;

public class Constants {
    /**
     * Constantes générales pour les fichiers CSV.
     */
    public static final String MAP_DATA_FILE_PATH_PROPERTY= "map.data.file.path";
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
    public static final double DEFAULT_MAX_DISTANCE = 10.0; //distance maximale par défaut entre une coordonnée et une station, en km
    public static final double DEFAULT_MIN_DISTANCE = 0.0; //distance minimale par défaut à marcher pendant le trajet, en km
}
