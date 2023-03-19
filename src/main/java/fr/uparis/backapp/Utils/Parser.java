package fr.uparis.backapp.Utils;

import fr.uparis.backapp.config.Config;
import fr.uparis.backapp.model.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

import static fr.uparis.backapp.Utils.Constants.Constants.*;

/**
 * Permet de parser les donnees du client et de creer nos Objets.
 */
public class Parser {

    private static Parser instance = null;

    private Map<Ligne, Set<Station>> carte;
    private Set<Section> sectionsSet;

    /**
     * Constructeur privé pour créer une instance de la classe Parser.
     * Initialise les structures de données carte et sectionsSet.
     */
    private Parser() {
        carte = new LinkedHashMap<>();
        sectionsSet = new LinkedHashSet();
    }

    /**
     * Renvoie l'instance unique de la classe Parser.
     * Si l'instance n'existe pas encore, elle est créée et la méthode parse() est appelée pour la remplir.
     *
     * @return l'instance unique de la classe Parser
     */
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
            instance.parse();
        }
        return instance;
    }

    /**
     * Renvoie la carte de réseau
     *
     * @return la carte de réseau
     */
    public Map<Ligne, Set<Station>> getCarte() {
        return carte;
    }

    /**
     * Renvoie les sections du réseau
     *
     * @return les sections du réseau
     */
    public Set<Section> getSections() {
        return sectionsSet;
    }

    /**
     * Lecture du Reseau.
     * lit la description d’un reseau de transport arbitraire.
     *
     */
    public void lect_Net() {
    }

    /**
     * Lecture des horaires.
     * Affiche les horaires de passage d’un transport a une Station donnee.
     */
    public void lect_Time() {
    }

    /**
     * Analyse le fichier de données de la carte et construit les objets Java correspondants.
     * La méthode lit le fichier de données de la carte, récupère chaque ligne et crée les objets Java correspondants :
     * les stations de départ et d'arrivée, la ligne, la durée de la section, et la section elle-même.
     * Les objets Java créés sont ensuite stockés dans des ensembles et des cartes pour une récupération rapide ultérieure.
     * Les coordonnées géographiques de chaque station sont également extraites à partir du fichier de données et stockées
     * dans les objets Station correspondants.
     */
    private void parse() {
        Config config = Config.getInstance();
        List<String[]> lines = getFileLines(config.getProperty(CSV_FILE_PATH_PROPERTY));

        lines.stream().forEach(l -> {
            Station stationDepart = new Station(l[STATION_DEPART_INDEX], Coordonnee.getCoordonnesFromString(l[STATION_DEPART_COORDONEES_INDEX]), null);
            Station stationArrivee = new Station(l[STATION_ARRIVEE_INDEX], Coordonnee.getCoordonnesFromString(l[STATION_ARRIVEE_COORDONEES_INDEX]), null);

            Ligne ligne = new Ligne(l[NOM_LIGNE_INDEX]);
            Set<Station> stationsSet = carte.getOrDefault(ligne, new LinkedHashSet<>());
            stationsSet.add(stationDepart);
            stationsSet.add(stationArrivee);

            ligne.setStations(stationsSet);
            carte.put(ligne, stationsSet);

            LocalTime duree = LocalTime.parse(correctTime(l[DUREE_INDEX]));
            Section section = new Section(stationDepart, stationArrivee, duree, ligne);
            sectionsSet.add(section);
        });
    }

    /**
     * Récupère les lignes d'un fichier CSV et les retourne sous forme de liste de tableaux de chaînes de caractères.
     *
     * @param filePath le chemin d'accès complet du fichier CSV à lire
     * @return une liste de tableaux de chaînes de caractères, chaque tableau représentant une ligne du fichier CSV
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture du fichier CSV
     */
    private static List<String[]> getFileLines(String filePath) {
        List<String[]> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(filePath)).
                    stream().
                    map(line -> line.split(DELIMITER)).
                    toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Convertit une chaîne de caractères de temps en une chaîne de temps correctement formatée avec le format "heure:minute:seconde".
     *
     * @param time la chaîne de temps à corriger dans le format "minute:seconde"
     * @return la chaîne de temps corrigée dans le format "heure:minute:seconde"
     * @throws NumberFormatException si la chaîne de temps d'entrée ne peut pas être analysée en entiers pour les minutes et les secondes
     */
    private String correctTime(String time) {
        //Make a string correct time with the format hour : minute : second
        String correctedTime = String.format("%1$02d:%2$02d:%3$02d",
                0,
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));
        return correctedTime;
    }

}
