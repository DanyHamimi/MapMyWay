package fr.uparis.backapp.utils;

import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Ligne;
import fr.uparis.backapp.model.section.SectionTransport;
import fr.uparis.backapp.utils.constants.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe outils, rassemblant les fonctions génériques du projet.
 */
public class Utils {
    /**
     * Arrondit le nombre en entrée et renvoie le même nombre avec une précision de trois chiffres après la virgule.
     *
     * @param number le nombre à tronquer.
     * @return le nombre arrondi au supérieur avec une précision de 3 chiffres après la virgule.
     */
    public static double truncateDoubleTo3Precision(Double number) {
        return new BigDecimal(number).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Convertit une chaîne de caractères de distance en dizaine de km, en une distance en km.
     *
     * @param distance la chaîne de caractère de distance à convertir.
     * @return une distance en km, avec une précision de 3 chiffres après la virgule.
     */
    public static double correctDistance(String distance) {
        return truncateDoubleTo3Precision(Double.parseDouble(distance) / 10);
    }

    /**
     * Convertit une chaîne de caractères de temps (au format dizaine de secondes) en une durée.
     *
     * @param time la chaîne de temps à convertir en durée.
     * @return la durée décrite par la chaîne de caractères en entrée, à la seconde supérieure en présence de millisecondes.
     * @throws NumberFormatException si la chaîne de temps d'entrée ne peut pas être analysée en entiers pour les minutes et les secondes.
     */
    public static Duration correctDuration(String time) {
        double seconds = Double.parseDouble(time.replace(':', '.')) * 10;
        int approximativeSeconds = (int) Math.ceil(seconds);
        return Duration.ofMinutes(approximativeSeconds / 60).plusSeconds(approximativeSeconds % 60);
    }

    /**
     * Convertit une chaîne de caractères représentant l'heure au format "hh:mm" en une chaîne de caractères au même format, avec des zéros ajoutés devant l'heure et les minutes si nécessaire.
     *
     * @param time la chaîne de caractères représentant l'heure à mettre dans le format souhaité.
     * @return la chaîne de caractères représentant l'heure corrigée, au format "hh:mm".
     * @throws NumberFormatException si la chaîne de caractères passée en argument n'est pas au format "hh:mm", où "hh" représente l'heure en format 24 heures et "mm" représente les minutes.
     */
    public static String correctTime(String time) {
        String[] times = time.split(":");
        return String.format("%1$02d:%2$02d", Integer.parseInt(times[0]), Integer.parseInt(times[1]));
    }

    /**
     * Calcule la distance entre deux coordonnées, avec une précision au mètre.
     *
     * @param origine     coordonnée du point de départ.
     * @param destination coordonnée du point d'arrivée.
     * @return la distance entre origine et destination en km, avec une précision de 3 chiffres après la virgule.
     */
    public static double distanceBetween(Coordonnee origine, Coordonnee destination) {
        double latOrigine = origine.getLatitudeRadian(), longOrigine = origine.getLongitudeRadian();
        double latDestination = destination.getLatitudeRadian(), longDestination = destination.getLongitudeRadian();
        double latDiff = latDestination - latOrigine, longDiff = longDestination - longOrigine;

        double halfLatSin = Math.sin(latDiff / 2), halfLongSin = Math.sin(longDiff / 2);
        double a = halfLatSin * halfLatSin + halfLongSin * halfLongSin * Math.cos(latOrigine) * Math.cos(latDestination);
        double res = 6372.795; //rayon moyen de la Terre en km
        res *= 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return truncateDoubleTo3Precision(res);
    }

    /**
     * Renvoie la durée de marche moyenne d'une distance en km.
     *
     * @param distance la distance à parcourir à pied.
     * @return la durée de marche moyenne pour parcourir une certaine distance.
     */
    public static Duration walkingDurationOf(double distance) {
        long dureeEnSecondes = (long) (3600 * distance / Constants.AVERAGE_WALKING_SPEED);
        return Duration.ofSeconds(dureeEnSecondes);
    }

    /**
     * Convertit une durée en une distance moyenne de marche en km.
     *
     * @param duration la durée à convertir en distance moyenne de marche.
     * @return la distance moyenne de marche, en km, avec une précision de 3 chiffres après la virgule.
     */
    public static double distanceOfWalkingDuration(Duration duration) {
        if (duration.isNegative()) return 0;
        return truncateDoubleTo3Precision(((double) duration.getSeconds()) / 3600 * Constants.AVERAGE_WALKING_SPEED);
    }

    /**
     * Renvoie les horaires de passages des lignes de transport en commun.
     *
     * @param sectionsTransports la durée à convertir en distance moyenne de marche.
     * @return les horaires de passages des lignes de transport en commun .
     */
    public static Map<String, List<LocalTime>> getSchedulesByLine(List<SectionTransport> sectionsTransports) {
        Map<String, List<LocalTime>> horairesByLine = new HashMap<>();
        for (SectionTransport sectionTransport : sectionsTransports) {
            String lineName = getLineNameWithDirection(sectionTransport.getLigne());
            List<LocalTime> horaires = horairesByLine.getOrDefault(lineName, new ArrayList<>());
            horaires.addAll(sectionTransport.getHorairesDepart());
            horaires.sort(LocalTime::compareTo);
            horairesByLine.put(lineName, horaires);
        }
        return horairesByLine;
    }

    private static String getLineNameWithDirection(Ligne line) {
        return line.getNomLigne().split(" ")[0] + ";" + line.getDirection().getNomLieu();
    }
}
