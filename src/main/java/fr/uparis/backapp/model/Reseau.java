package fr.uparis.backapp.model;

import fr.uparis.backapp.utils.Parser;

import java.time.LocalTime;
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
        Collections.addAll(stations, parser.getStations());
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
     * Retrouve une station dans le réseau avec le nom de la station.
     * @param nameStation le nom de la station cherchée.
     * @return la station qui porte le nom qu'on cherche.
     */
    public Station getStation(String nameStation) {
        for(Station s: stations)
            if(s.getNomStation().equals(nameStation))
                return s;
        return null;
    }

    /**
     * Retrouve une station dans le réseau avec les coordonnées de la station.
     * @param coordonneeStation la coordonnée de la station cherchée.
     * @return la station qui se trouve à la coordonnée précisée.
     */
    public Station getStation(Coordonnee coordonneeStation) {
        for(Station s: stations)
            if(s.getLocalisation().equals(coordonneeStation))
                return s;
        return null;
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
     * Détermine le plus court chemin pour se rendre d'une Station à une autre en connaissant le Reseau.
     * Prend en compte les horaires de trains, mais pas les temps de changements.
     * @param stationDepart la station de départ.
     * @param stationArrivee la station d'arrivée.
     * @return la liste des plus courts chemins ou null si aucun chemin n'a été trouvé.
     */
    public List<Section> djikstra(Station stationDepart, Station stationArrivee, LocalTime horaireDepart) {
        //Initialisation
        Map<Station, LocalTime> myMap = new HashMap<>();
        for(Station station : stations) myMap.put(station, null); //null représente ici un temps infini
        myMap.put(stationDepart, horaireDepart);

        //Station, la meilleure liste de section pour y accéder
        Map<Station, List<Section>> trace = new Hashtable<>();
        trace.put(stationDepart, new LinkedList<>());

        Station nextStation;
        LocalTime nextHoraire;
        //Prendre l'élément le plus petit.
        while(!myMap.isEmpty() && (nextStation = min(myMap)) != null) {
            nextHoraire = myMap.get(nextStation);
            nextStation.setHoraireDePassage(nextHoraire);

            //Si c'est la destination, c'est gagné!
            if(nextStation.equals(stationArrivee))
                return trace.get(nextStation);
            //Sinon, on examine les voisins
            else {
                List<Section> currentSections = trace.get(nextStation);
                for(Section section : nextStation.getCorrespondances()) {
                    //On ne veut que ceux qui sont encore dans myMap, et que le prochain train
                    Station target = section.getStationArrivee();
                    if(myMap.containsKey(target)) {
                        LocalTime prochainDepart = section.getHoraireProchainDepart(nextHoraire);
                        if(prochainDepart != null) {
                            prochainDepart = prochainDepart.plus(section.getDuree());
                            if(myMap.get(target) == null) myMap.put(target, prochainDepart);
                            else if(myMap.get(target).isAfter(prochainDepart)) myMap.replace(target, prochainDepart);

                            List<Section> sections = new LinkedList<>(currentSections);
                            sections.add(section);
                            trace.put(target, sections);
                        }
                    }
                }
            }
            myMap.remove(nextStation);
        }
        return null; //pas de trajet possible à cause de l'heure des transports
    }

    /**
     * Trouve l'élément de poids minimal du dictionnaire.
     * @param myMap une map telle que celle utilisée dans l'algorithme de djikstra.
     * @return La station avec le plus petit LocalTime.
     */
    private Station min(Map<Station, LocalTime> myMap) {
        Station stationMin = null;
        LocalTime tempsMin = null;

        Station stationTmp;
        LocalTime tempsTmp;
        for(Map.Entry<Station, LocalTime> entry : myMap.entrySet()) {
            stationTmp = entry.getKey();
            tempsTmp = entry.getValue();
            if(stationMin == null) {
                stationMin = stationTmp;
                tempsMin = tempsTmp;
            }
            else if(tempsMin == null || (tempsTmp != null && tempsMin.isAfter(tempsTmp))) {
                stationMin = stationTmp;
                tempsMin = entry.getValue();
            }
        }
        return (tempsMin == null)? null : stationMin;
    }
}
