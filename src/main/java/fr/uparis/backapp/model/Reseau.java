package fr.uparis.backapp.model;

import fr.uparis.backapp.utils.Parser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static fr.uparis.backapp.utils.Utils.distanceBetween;

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
        for(Station station: parser.getStations()) stations.add(station);
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
                                         .filter(s -> s.isStationDepart(station) || s.isStationArrivee(station))
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
        if(sections.stream().noneMatch(s -> s.isStationDepart(section.getStationDepart()) || s.isStationArrivee(section.getStationDepart())))
            removeStation(section.getStationDepart());
        if(sections.stream().noneMatch(s -> s.isStationDepart(section.getStationArrivee()) || s.isStationArrivee(section.getStationArrivee())))
            removeStation(section.getStationArrivee());
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
