package fr.uparis.backapp.model;

import java.time.LocalTime;
import java.util.*;

/**
 * Represente une Ligne du Reseau de transport
 */
public class Ligne {
    private String nomLigne; //nom de la ligne, unique
    private Set<Station> stations; //stations de la ligne
    private List<LocalTime> tempsDeparts;//TODO it will maybe deleted, to be discussed

    /**
     * Constructeur de la classe Ligne
     * @param nomLigne nom de la Ligne
     * @param stations liste des Station desservies par la Ligne
     * @param tempsDeparts liste des horaires de departs de cette Ligne
     */
    public Ligne(String nomLigne, Set<Station> stations, List<LocalTime> tempsDeparts) {
        this.nomLigne = nomLigne;
        this.stations = stations;
        this.tempsDeparts = tempsDeparts;
    }

    public Ligne(String nomLigne){
        this.nomLigne = nomLigne;
        this.stations = new LinkedHashSet<>();
        this.tempsDeparts = new ArrayList<>();
    }

    /**
     * Renvoie le nom de la Ligne
     * @return le nom de la Ligne
     */
    public String getNomLigne() {
        return nomLigne;
    }

    /**
     * Met a jour le nom de la Ligne
     * @param nomLigne nom de la Ligne
     */
    public void setNomLigne(String nomLigne) {
        this.nomLigne = nomLigne;
    }

    /**
     * Ajout d'une station à la ligne, si elle n'y est pas déjà.
     * @param station la station à ajouter à la ligne
     */
    public void addStation(Station station) {
        for(Station s : stations) {
            if(s.getNomStation().equals(station.getNomStation())) {
                return;
            }
        }
        this.stations.add(station);
    }

    /**
     * Renvoie la liste des Station de la Ligne
     * @return la liste des Station de la Ligne
     */
    public Set<Station> getStations() {
        return stations;
    }

    /**
     * Met a jour la liste des Station de la Ligne
     * @param stations liste des Station de la Ligne
     */
    public void setStations(Set<Station> stations) {
        this.stations = stations;
    }

    /**
     * Renvoie la liste des horaires de depart de la Ligne
     * @return la liste des horaires de depart de la Ligne
     */
    public List<LocalTime> getTempsDeparts() {
        return tempsDeparts;
    }

    /**
     * Met a jour la liste des horaires de depart de la Ligne
     * @param tempsDeparts liste des horaires de depart de la Ligne
     */
    public void setTempsDeparts(List<LocalTime> tempsDeparts) {
        this.tempsDeparts = tempsDeparts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ligne ligne = (Ligne) o;

        if (!Objects.equals(nomLigne, ligne.nomLigne)) return false;
//        if (!Objects.equals(stations, ligne.stations)) return false;
//        return Objects.equals(tempsDeparts, ligne.tempsDeparts);
        return true;
    }

    @Override
    public String toString() {
        String s = nomLigne + " : ";
        for(Station station :stations ){
            s+= station.getNomStation()+" -> ";
        }
        return s;
    }
}
