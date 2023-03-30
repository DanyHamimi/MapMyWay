package fr.uparis.backapp.model;

import fr.uparis.backapp.Utils.Parser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Représente un Reseau de transport
 */
public class Reseau {
    private static Reseau instance = null;
    private static Set<Station> stations;
    private static Set<Section> sections;


    /**
     * Constructeur privé pour créer une instance de la classe Reseau.
     */
    private Reseau() {
        Parser parser = Parser.getInstance();
        sections = parser.getSections();
        stations = new HashSet<>();
        parser.getStations().forEach(this::addStation);
    }

    /**
     * Renvoie l'instance de la classe Reseau.
     * @return l'instance de la classe Reseau.
     */
    public static Reseau getInstance() {
        if (instance == null) instance = new Reseau();
        return instance;
    }

    /**
     * Renvoie la liste des Station du Reseau.
     * @return la liste des Station du Reseau.
     */
    public Set<Station> getStations() {
        return stations;
    }

    /**
     * Ajout d'une station dans le Reseau, si elle n'y est pas déjà.
     * @param station la station à ajouter dans le Reseau.
     */
    public void addStation(Station station) {
        stations.add(station);
    }

    /**
     * Suppression d'une station dans le Reseau, si elle existe,
     * et des sections impactées
     * @param station la station à supprimer du Reseau.
     */
    public void removeStation(Station station) {
        stations.remove(station);
        List<Section> toDelete = sections.stream()
                                         .filter(s -> s.isFrom(station) || s.isGoingTo(station))
                                         .toList();
        for(Section s: toDelete) removeSection(s);
    }

    /**
     * Renvoie la liste des Section du Reseau.
     * @return la liste des Section du Reseau.
     */
    public Set<Section> getSections() {
        return sections;
    }

    /**
     * Ajout d'une section dans le Reseau, si elle n'y est pas déjà,
     * et également ajout des stations qui la composent.
     * @param section la section à ajouter dans le Reseau.
     */
    public void addSection(Section section) {
        sections.add(section);
        addStation(section.getStationDepart());
        addStation(section.getStationArrivee());
    }

    /**
     * Suppression d'une section dans le Reseau, si elle existe,
     * et également les stations qui la composent si plus utile.
     * @param section la section à supprimer du Reseau.
     */
    public void removeSection(Section section) {
        sections.remove(section);
        if(sections.stream().noneMatch(s -> s.isFrom(section.getStationDepart()) || s.isGoingTo(section.getStationDepart())))
            removeStation(section.getStationDepart());
        if(sections.stream().noneMatch(s -> s.isFrom(section.getStationArrivee()) || s.isGoingTo(section.getStationArrivee())))
            removeStation(section.getStationArrivee());
    }

    /**
     * Calcule la distance entre deux coordonnées, avec une précision au mètre.
     * @param origine coordonnée du point de départ.
     * @param destination coordonnée du point d'arrivée.
     * @return la distance entre origine et destination en km, avec au plus 3 chiffres après la virgule.
     */
    private static double distanceBetween(Coordonnee origine, Coordonnee destination) {
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
     * @param coordonnee coordonnée du point de départ.
     * @param maxDistance distance maximale acceptable entre deux points, en km.
     * @param minDistance distance minimale acceptable entre deux points, en km.
     * @return la liste des stations dont la distance est majorée par maxDistance et minorée par minDistance.
     */
    public List<Station> getNearStations(Coordonnee coordonnee, double maxDistance, double minDistance) {
        List<Station> nearStations = new ArrayList<>();
        double distance;
        for (Station s : stations) {
            distance = distanceBetween(coordonnee, s.getLocalisation());
            if (distance >= minDistance && distance <= maxDistance)
                nearStations.add(s);
        }

        return nearStations;
    }

    /**
     * Determine le plus court chemin pour se rendre d'une Coordonnee a une autre en connaissant le Reseau.
     * @return la liste des plus courts chemins.
     */
    public List<Section> djikstra() {
        //TODO define the function
        return null;
    }
}