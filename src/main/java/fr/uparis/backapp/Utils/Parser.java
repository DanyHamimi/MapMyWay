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

    private Config config = Config.getInstance();
    private final int nbElem = 7;
    String filename = "src/Metro91.csv";

    /**
     * Lecture du Reseau.
     * lit la description d’un reseau de transport arbitraire.
     */
    public void lect_Net(Reseau r) {
    }

    public String correctTime(String time) {
        //Make a string correct time with the format hour : minute : second
        String correctedTime = String.format("%1$02d:%2$02d:%3$02d",
                0,
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));
        return correctedTime;
    }

    /**
     * Lecture des horaires.
     * Affiche les horaires de passage d’un transport a une Station donnee.
     */
    public void lect_Time() {
    }

    public void parse() {
        Map<String, Set<Station>> carte = new HashMap<>();
        List<Section> sectionsList = new ArrayList<>();
        List<String[]> lines = getFileLines(config.getProperty("map.data.file.path"));

        lines.stream().forEach(l -> {
            Station stationDepart = new Station(l[STATION_DEPART_INDEX], Coordonnee.getCoordonnesFromString(l[STATION_DEPART_COORDONEES_INDEX]), null);
            Station stationArrivee = new Station(l[STATION_ARRIVEE_INDEX], Coordonnee.getCoordonnesFromString(l[STATION_ARRIVEE_COORDONEES_INDEX]), null);

            Set<Station> stationsSet = carte.getOrDefault(l[NOM_LIGNE_INDEX], new LinkedHashSet<>());
            stationsSet.add(stationDepart);
            stationsSet.add(stationArrivee);

            carte.put(l[NOM_LIGNE_INDEX], stationsSet);

            LocalTime duree = LocalTime.parse(correctTime(l[DUREE_INDEX]));
//            Section section = new Section(stationDepart, stationArrivee, duree, );

//            sectionsList.add(section);
        });

        carte.forEach((ligne, stations) -> {
            System.out.print(ligne + " : ");
            stations.forEach(s -> System.out.print(s + " -> "));
            System.out.println();
        });
    }

    private List<String[]> getFileLines(String filePath) {
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
}
