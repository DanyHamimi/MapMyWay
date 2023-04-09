package fr.uparis.backapp.Utils.constants;

public class Constants {
    public static final String DELIMITER = ";";
    public static final String CSV_FILE_PATH_PROPERTY = "map.data.file.path";
    public static final int STATION_DEPART_INDEX = 0;
    public static final int STATION_ARRIVEE_INDEX = 2;
    public static final int STATION_DEPART_COORDONEES_INDEX = 1;
    public static final int STATION_ARRIVEE_COORDONEES_INDEX = 3;
    public static final int NOM_LIGNE_INDEX = 4;
    public static final int DUREE_INDEX = 5;
    public static final int DISTANCE_INDEX = 6;
    public static final int POS = 5;
    public static final double DEFAULT_MAX_DISTANCE = 10.0; //distance maximale par défaut entre une coordonnée et une station, en km
    public static final double DEFAULT_MIN_DISTANCE = 0.0; //distance minimale par défaut à marcher pendant le trajet, en km

//    private Constants() {
//        throw new IllegalCallerException("Utilities exception");
//    }
}
