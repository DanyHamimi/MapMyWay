package fr.uparis.backapp.model;

import fr.uparis.backapp.Utils.Parser;

import javax.sound.sampled.Line;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Represente un Reseau de transport
 */
public class Reseau {
    //lien vers le patern singleton ===> https://fr.wikipedia.org/wiki/Singleton_(patron_de_conception)
    private static Reseau instance = null;
    private static Set<Station> stations;
    private static Set<Section> sections;


    /**
     * Constructeur privé pour créer une instance de la classe Reseau.
     */
    private Reseau() {
        stations = new LinkedHashSet<>();
    }

    /**
     * Renvoie l'instance de la classe Reseau
     *
     * @return l'instance de la classe Reseau
     */
    public static Reseau getInstance() {
        if (instance == null) {
            instance = new Reseau();

            Parser parser = Parser.getInstance();
            sections = parser.getSections();

            parser.getCarte().
                    forEach((ligne, s) -> stations.addAll(s));
        }
        return instance;
    }

    /**
     * Ajout d'une station dans le réseau, si elle n'y est pas déjà.
     *
     * @param station la station à ajouter dans le réseau
     */
    public void addStation(Station station) {
        for (Station s : stations) {
            if (s.equals(station)) {
                return;
            }
        }
        this.stations.add(station);
    }

    /**
     * Ajout d'une section dans le réseau
     *
     * @param section
     */
    public void addSection(Section section) {
        this.sections.add(section);
    }

    /**
     * Afficher les stations du réseau de transport
     */
    public void printStations() {
        stations.forEach(s -> System.out.print(s + " "));
    }

    /**
     * Afficher toutes les sections (segments) du réseau de transport
     */
    public void printSections() {
        for (Section section : sections) {
            System.out.println(section.getStationDepart().getNomStation() + " -> " + section.getStationArrivee().getNomStation() + " Distance " + section.getDistance() + "km" + " Temps " + section.getDuree() + "min, LIGNE : " + section.getLigne().getNomLigne());
        }
    }

    /**
     * Calcule la distance entre deux coordonnées, avec une précision au mètre.
     *
     * @param origine     coordonnée du point de départ
     * @param destination coordonnée du point d'arrivée
     * @return la distance entre origine et destination en km, avec au plus 3 chiffres après la virgule
     */
    static double distanceBetween(Coordonnee origine, Coordonnee destination) {
        double latOrigine = origine.getLatitudeRadian(), longOrigine = origine.getLongitudeRadian();
        double latDestination = destination.getLatitudeRadian(), longDestination = destination.getLongitudeRadian();
        double latDiff = latDestination - latOrigine, longDiff = longDestination - longOrigine;

        double halfLatSin = Math.sin(latDiff / 2), halfLongSin = Math.sin(longDiff / 2);
        double a = halfLatSin * halfLatSin + halfLongSin * halfLongSin * Math.cos(latOrigine) * Math.cos(latDestination);
        double res = 6372.795; //rayon moyen de la Terre en km
        res *= 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return new BigDecimal(res).setScale(3, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * Cherche les stations proches d'une coordonnée.
     *
     * @param coordonnee  coordonnée du point de départ
     * @param maxDistance distance maximale acceptable entre deux points, en km
     * @param minDistance distance minimale acceptable entre deux points, en km
     * @return la liste des stations dont la distance est majorée par maxDistance et minorée par minDistance
     */
    public static Station[] getNearStations(Coordonnee coordonnee, double maxDistance, double minDistance) {
        List<Station> nearStations = new ArrayList<>();
        double distance;
        for (Station s : stations) {
            distance = distanceBetween(coordonnee, s.getLocalisation());
            if (distance >= minDistance && distance <= maxDistance)
                nearStations.add(s);
        }

        return (Station[]) nearStations.toArray();
    }

    /**
     * Determine le plus court chemin pour se rendre d'une Coordonnee a une autre en connaissant le Reseau
     *
     * @return la liste des plus courts chemins
     */
    public List<Section> djikstra() {
        //TODO define the function
        return null;
    }

}
