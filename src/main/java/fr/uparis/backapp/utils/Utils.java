package fr.uparis.backapp.utils;

import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Section;
import fr.uparis.backapp.model.Station;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Set;

public class Utils{
    /**
     * Arrondit le nombre en entrée et renvoie le même nombre avec une précision de trois chiffres après la virgule.
     * @param number le nombre à tronquer.
     * @return le nombre arrondi au supérieur avec une précision de 3 chiffres après la virgule.
     */
    public static double truncateDoubleTo3Precision(Double number){
        return new BigDecimal(number).setScale(3, RoundingMode.HALF_UP).doubleValue();
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
     * Convertit une chaîne de caractères de distance en dizaine de km, en une distance en km.
     *
     * @param distance la chaîne de caractère de distance à convertir.
     * @return une distance en km, avec une précision de 3 chiffres après la virgule.
     */
    public static double correctDistance(String distance) {
        return truncateDoubleTo3Precision(Double.parseDouble(distance) / 10);
    }

    /**
     * Récupère la section de départ correspondant à une station de départ et une variante de ligne donnée.
     *
     * @param sections     un ensemble de sections parmi lesquelles chercher la section de départ.
     * @param nameStation  le nom de la station de départ recherchée.
     * @param lineVariant  le variant de ligne de la section de départ recherchée.
     * @return la section de départ ayant pour station de départ et variant de ligne ceux donnés, ou null si aucune section n'a été trouvée.
     */
    public static Section findSectionDepart(Set<Section> sections, String nameStation, String lineVariant) {
        Station stationDepart = new Station(nameStation, null);
        for (Section section : sections)
            if (section.isStationDepart(stationDepart) && section.getLigne().getNomLigne().equals(lineVariant))
                return section;
        return null;
    }

    /**
     * Convertit une chaîne de caractères représentant l'heure au format "hh:mm" en une chaîne de caractères au même format, avec des zéros ajoutés devant l'heure et les minutes si nécessaire.
     *
     * @param time la chaîne de caractères représentant l'heure à mettre dans le format souhaité.
     * @return la chaîne de caractères représentant l'heure corrigée, au format "hh:mm".
     * @throws NumberFormatException si la chaîne de caractères passée en argument n'est pas au format "hh:mm", où "hh" représente l'heure en format 24 heures et "mm" représente les minutes.
     */
    public static String correctTime(String time) {
        //Make a string correct time with the format hour : minute
        return String.format("%1$02d:%2$02d",
                             Integer.parseInt(time.split(":")[0]),
                             Integer.parseInt(time.split(":")[1]));
    }

    /**
     * Calcule la distance entre deux coordonnées, avec une précision au mètre.
     * @param origine coordonnée du point de départ.
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
}
