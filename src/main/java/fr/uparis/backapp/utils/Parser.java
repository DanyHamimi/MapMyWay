package fr.uparis.backapp.utils;

import fr.uparis.backapp.utils.constants.Constants;
import fr.uparis.backapp.config.Config;
import fr.uparis.backapp.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static fr.uparis.backapp.utils.constants.Constants.*;
import static fr.uparis.backapp.utils.Utils.*;

/**
 * Permet de parser les donnees du client et de creer nos Objets.
 */
public class Parser {
    private static Parser instance = null;
    final private Map<String, Ligne> carte;
    final private Map<String, Station> stationMap;
    final private Set<Section> sectionsSet;

    /**
     * Constructeur privé pour créer une instance de la classe Parser.
     * Initialise les structures de données carte et sectionsSet.
     */
    private Parser() {
        carte = new LinkedHashMap<>();
        stationMap = new HashMap<>();
        sectionsSet = new LinkedHashSet<>();
    }

    /**
     * Renvoie l'instance unique de la classe Parser.
     * Si l'instance n'existe pas encore, elle est créée et la méthode parse() est appelée pour la remplir.
     *
     * @return l'instance unique de la classe Parser.
     */
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
            instance.parseMap();
            instance.parseTime();
        }
        return instance;
    }

    /**
     * Renvoie les lignes du réseau.
     *
     * @return les lignes du réseau.
     */
    public Ligne[] getLignes() {
        Ligne[] lignes = new Ligne[carte.size()];
        return carte.values().toArray(lignes);
    }

    /**
     * Renvoie les sections du réseau.
     *
     * @return les sections du réseau.
     */
    public Set<Section> getSections() {
        return sectionsSet;
    }

    /**
     * Renvoie les stations du réseau.
     *
     * @return les stations du réseau.
     */
    public Station[] getStations() {
        List<Station> stations = stationMap.values().stream().toList();
        Station[] res = new Station[stations.size()];
        for (int i = 0; i < stations.size(); i++) res[i] = stations.get(i);
        return res;
    }

    /**
     * Récupère les lignes d'un fichier CSV et les retourne sous forme de liste de tableaux de chaînes de caractères.
     *
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Analyse le fichier de données de la carte et construit les objets Java correspondants.
     * La méthode lit le fichier de données de la carte, récupère chaque ligne et crée les objets Java suivants :
     * les stations de départ et d'arrivée, la ligne, la durée de la section, et la section elle-même.
     * Les objets Java créés sont ensuite stockés dans des ensembles et des cartes pour une récupération rapide ultérieure.
     * Les coordonnées géographiques de chaque station sont également extraites à partir du fichier de données et stockées
     * dans les objets Station correspondants.
     */
    private void parseMap() {
        Config config = Config.getInstance();
        List<String[]> lines = getFileLines(config.getProperty(Constants.MAP_DATA_FILE_PATH_PROPERTY));

        lines.forEach(l -> {
            Station stationDepart = stationMap.getOrDefault(l[Constants.STATION_DEPART_INDEX],
                    new Station(l[Constants.STATION_DEPART_INDEX], new Coordonnee(l[Constants.STATION_DEPART_COORDONEES_INDEX])));
            Station stationArrivee = stationMap.getOrDefault(l[Constants.STATION_ARRIVEE_INDEX],
                    new Station(l[Constants.STATION_ARRIVEE_INDEX], new Coordonnee(l[Constants.STATION_ARRIVEE_COORDONEES_INDEX])));

            Ligne ligne = carte.getOrDefault(l[Constants.NOM_LIGNE_INDEX], new Ligne(l[Constants.NOM_LIGNE_INDEX]));
            ligne.addStation(stationDepart);
            ligne.addStation(stationArrivee);
            carte.put(l[Constants.NOM_LIGNE_INDEX], ligne);

            Duration duree = correctDuration(l[Constants.DUREE_INDEX]);
            double distance = correctDistance(l[Constants.DISTANCE_INDEX]);

            Section section = new Section(stationDepart, stationArrivee, duree, distance, ligne);
            sectionsSet.add(section);

            stationDepart.addCorrespondance(section);
            //On ajoute le trajet inverse
            stationArrivee.addCorrespondance(new Section(stationArrivee, stationDepart, duree, distance, ligne));

            stationMap.put(l[Constants.STATION_DEPART_INDEX], stationDepart);
            stationMap.put(l[Constants.STATION_ARRIVEE_INDEX], stationArrivee);
        });
    }

    /**
     * Lecture des horaires.
     * Ajoute les horaires de passage d’un transport à une Station donnée.
     */
    private void parseTime() {
        Config config = Config.getInstance();
        List<String[]> fileLines = getFileLines(config.getProperty(SCHEDULES_FILE_PATH_PROPERTY));
        Map<String, Map<String, List<LocalTime>>> passagePerVariant = new HashMap<>();
        fileLines.forEach(l -> {
            String lineVariant = l[SCHEDULES_FILE_LINE_INDEX] + " variant " + l[SCHEDULES_FILE_VARIANTE_INDEX];
            Map<String, List<LocalTime>> lineDetail = passagePerVariant.getOrDefault(lineVariant, new HashMap<>());
            String terminal = l[SCHEDULES_FILE_TERMINUS_INDEX];
            List<LocalTime> departureTimeByTerminal = lineDetail.getOrDefault(terminal, new ArrayList<>());
            LocalTime time = LocalTime.parse(correctTime(l[SCHEDULES_FILE_TIME_INDEX]), DateTimeFormatter.ofPattern("HH:mm"));

            departureTimeByTerminal.add(time);
            lineDetail.put(terminal, departureTimeByTerminal);
            passagePerVariant.put(lineVariant, lineDetail);
        });

        addSchedulesToLines(passagePerVariant);//TODO discuss this one (elle peut etre enlever)
        calculate_schedules(passagePerVariant);
    }

    /**
     * Ajoute les horaires de départ de chaque ligne à la carte en fonction des horaires fournis sous forme de dictionnaire.
     *
     * @param map un dictionnaire contenant les horaires de départ pour chaque variant de ligne et chaque terminus.
     */
    private void addSchedulesToLines(Map<String, Map<String, List<LocalTime>>> map) {
        map.forEach((variant, timesByTerminal) ->
            timesByTerminal.values().forEach(times -> times.forEach(time -> carte.get(variant).addHoraireDepart(time)))
        );
    }

    /**
     * Calcule les horaires de départ pour chaque section du réseau à partir d'une carte des horaires de passage pour chaque combinaison de variant et terminus.
     *
     * @param map une carte des horaires de passage pour chaque combinaison de variant et terminus.
     * @throws IllegalArgumentException si l'une des stations de départ n'est pas présente dans le réseau.
     */
    private void calculate_schedules(Map<String, Map<String, List<LocalTime>>> map) {
        map.forEach((variant, timesByTerminal) ->
            timesByTerminal.forEach((terminal, times) -> {
                Section sectionDepart = findSectionDepart(sectionsSet, terminal, variant);
                if (sectionDepart == null)
                    throw new IllegalArgumentException("Station départ introuvable dans le réseau");
                sectionDepart.addHorairesDepart(times);
                propagateSchedules(sectionDepart);
            })
        );
    }

    /**
     * Calcule les horaires de départ de la section initiale et les propage à toutes les sections connectées à cette section,
     * en utilisant les durées des sections pour calculer les horaires de départ des sections suivantes,
     * et en rajoutant 40 secondes à chaque arrêt.
     *
     * @param sectionDepart la section de départ à partir de laquelle propager les horaires de départ.
     */
    private void propagateSchedules(Section sectionDepart) {
        Section currentSection = sectionDepart;
        Section nextSectionInTheSameLine = currentSection.moveToNextSectionInTheSameLine(sectionsSet);

        while (nextSectionInTheSameLine != null) {
            Section finalCurrentSection = currentSection;
            Section finalNextSectionInTheSameLine = nextSectionInTheSameLine;
            currentSection.getHorairesDepart().forEach(time ->
                    finalNextSectionInTheSameLine.addHoraireDepart(time.plus(finalCurrentSection.getDuree())
                                                                       .plus(Duration.ofSeconds(40)))); //40 secondes d'arrêt

            currentSection = nextSectionInTheSameLine;
            nextSectionInTheSameLine = currentSection.moveToNextSectionInTheSameLine(sectionsSet);
        }
    }
}
