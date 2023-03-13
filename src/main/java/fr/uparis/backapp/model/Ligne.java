package fr.uparis.backapp.model;

import java.time.LocalTime;
import java.util.List;

/**
 * Represente une Ligne du Reseau de transport
 */
public class Ligne {

    private String nomLigne;
    private List<Station> stations;
    private List<LocalTime> tempsDeparts;

    /**
     * Constructeur de la classe
     * @param nomLigne nom de la Ligne
     * @param stations liste des Station desservis par la Ligne
     * @param tempsDeparts liste des horaires de departs de cette Ligne
     */
    public Ligne(String nomLigne, List<Station> stations, List<LocalTime> tempsDeparts) {
        this.nomLigne = nomLigne;
        this.stations = stations;
        this.tempsDeparts = tempsDeparts;
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
     * Renvoie la liste des Station de la Ligne
     * @return la liste des Station de la Ligne
     */
    public List<Station> getStations() {
        return stations;
    }

    /**
     * Met a jour la liste des Station de la Ligne
     * @param stations liste des Station de la Ligne
     */
    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    /**
     * Renvoie la liste des horaires de departs de la Ligne
     * @return la liste des horaires de departs de la Ligne
     */
    public List<LocalTime> getTempsDeparts() {
        return tempsDeparts;
    }

    /**
     * Met a jour la liste des horaires de departs de la Ligne
     * @param tempsDeparts liste des horaires de departs de la Ligne
     */
    public void setTempsDeparts(List<LocalTime> tempsDeparts) {
        this.tempsDeparts = tempsDeparts;
    }

}
