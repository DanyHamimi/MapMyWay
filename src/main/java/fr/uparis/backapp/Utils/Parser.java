package fr.uparis.backapp.Utils;

import fr.uparis.backapp.Utils.constants.Constants;
import fr.uparis.backapp.config.Config;
import fr.uparis.backapp.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

/**
 * Permet de parser les donnees du client et de creer nos Objets.
 */
public class Parser {
    private static Parser instance = null;
    final private Map<String, Ligne> carte;
    final private Set<Section> sectionsSet;

    /**
     * Constructeur privé pour créer une instance de la classe Parser.
     * Initialise les structures de données carte et sectionsSet.
     */
    private Parser() {
        carte = new HashMap<>();
        sectionsSet = new HashSet<>();
    }

    /**
     * Renvoie l'instance unique de la classe Parser.
     * Si l'instance n'existe pas encore, elle est créée et la méthode parse() est appelée pour la remplir.
     * @return l'instance unique de la classe Parser.
     */
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
            instance.parse();
        }
        return instance;
    }

    /**
     * Renvoie les lignes du réseau.
     * @return les lignes du réseau.
     */
    public Ligne[] getLignes() {
        Ligne[] lignes = new Ligne[carte.size()];
        return carte.values().toArray(lignes);
    }

    /**
     * Renvoie les sections du réseau.
     * @return les sections du réseau.
     */
    public Set<Section> getSections() {
        return sectionsSet;
    }

    /**
     * Renvoie les stations du réseau, avec potentiellement des doublons.
     * @return les stations du réseau.
     */
    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();
        for(Ligne ligne: carte.values()) stations.addAll(ligne.getStations());
        return stations;
    }

    /**
     * Analyse le fichier de données de la carte et construit les objets Java correspondants.
     * La méthode lit le fichier de données de la carte, récupère chaque ligne et crée les objets Java suivants :
     * les stations de départ et d'arrivée, la ligne, la durée de la section, et la section elle-même.
     * Les objets Java créés sont ensuite stockés dans des ensembles et des cartes pour une récupération rapide ultérieure.
     * Les coordonnées géographiques de chaque station sont également extraites à partir du fichier de données et stockées
     * dans les objets Station correspondants.
     */
    private void parse() {
        Config config = Config.getInstance();
        List<String[]> lines = getFileLines(config.getProperty(Constants.CSV_FILE_PATH_PROPERTY));

        lines.forEach(l -> {
            Station stationDepart = new Station(l[Constants.STATION_DEPART_INDEX], new Coordonnee(l[Constants.STATION_DEPART_COORDONEES_INDEX]));
            Station stationArrivee = new Station(l[Constants.STATION_ARRIVEE_INDEX], new Coordonnee(l[Constants.STATION_ARRIVEE_COORDONEES_INDEX]));

            Ligne ligne = carte.getOrDefault(l[Constants.NOM_LIGNE_INDEX], new Ligne(l[Constants.NOM_LIGNE_INDEX]));
            ligne.addStation(stationDepart);
            ligne.addStation(stationArrivee);
            carte.put(l[Constants.NOM_LIGNE_INDEX], ligne);

            LocalTime duree = LocalTime.parse(correctTime(l[Constants.DUREE_INDEX]));
            double distance = Double.parseDouble(l[Constants.DISTANCE_INDEX]);

            Section section = new Section(stationDepart, stationArrivee, duree, distance, ligne);
            sectionsSet.add(section);
        });
    }

    /**
     * Récupère les lignes d'un fichier CSV et les retourne sous forme de liste de tableaux de chaînes de caractères.
     * @param filePath le chemin d'accès complet du fichier CSV à lire.
     * @return une liste de tableaux de chaînes de caractères, chaque tableau représentant une ligne du fichier CSV.
     */
    private static List<String[]> getFileLines(String filePath) {
        List<String[]> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(filePath))
                         .stream()
                         .map(line -> line.split(Constants.DELIMITER))
                         .toList();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Convertit une chaîne de caractères de temps en une chaîne de temps correctement formatée avec le format "heure:minute:seconde".
     * @param time la chaîne de temps à corriger dans le format "minute:seconde".
     * @return la chaîne de temps corrigée dans le format "heure:minute:seconde".
     * @throws NumberFormatException si la chaîne de temps d'entrée ne peut pas être analysée en entiers pour les minutes et les secondes.
     */
    private String correctTime(String time) {
        String[] times = time.split(":");
        //Make a string correct time with the format hour : minute : second
        return String.format("%1$02d:%2$02d:%3$02d",
                              0,
                              Integer.parseInt(times[0]),
                              Integer.parseInt(times[1]));
    }

    /**
     * Lecture des horaires.
     * Affiche les horaires de passage d’un transport à une Station donnée.
     */
    public void lect_Time() {
        //TODO
    }
}
