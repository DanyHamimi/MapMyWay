package fr.uparis.backapp.model;

import java.time.LocalTime;

/**
 * Represente une Section du Reseau de transport
 */
public class Section {

    private Station stationDepart;
    private Station stationArrivee;
    private LocalTime duree; // maybe it will be changed to double (number of minutes)
    private double distance;
    private Ligne ligne;

    /**
     * Constructeur de la Section
     * @param stationDepart Station de depart de la Section
     * @param stationArrivee Station d'arrivee de la Section
     * @param duree duree estimee de la Section
     * @param distance distance de la Section
     * @param ligne Ligne a utiliser
     */
    public Section(Station stationDepart, Station stationArrivee, LocalTime duree, double distance,Ligne ligne) {
        this.stationDepart = stationDepart;
        this.stationArrivee = stationArrivee;
        this.duree = duree;
        this.distance = distance;
        this.ligne = ligne;

    }

    /**
     * Renvoie la Station de depart de la Section
     * @return la Station de depart de la Section
     */
    public Station getStationDepart() {
        return stationDepart;
    }

    /**
     * Met a jout la Station de depart de la Section
     * @param stationDepart Station de depart de la Section
     */
    public void setStationDepart(Station stationDepart) {
        this.stationDepart = stationDepart;
    }

    /**
     * Renvoie la Station d'arrivee de la Section
     * @return la Station d'arrivee de la Section
     */
    public Station getStationArrivee() {
        return stationArrivee;
    }

    /**
     * Met a jour la Station d'arrivee de la Section
     * @param stationArrivee Station d'arrivee de la Section
     */
    public void setStationArrivee(Station stationArrivee) {
        this.stationArrivee = stationArrivee;
    }

    /**
     * Renvoie la duree de la Section
     * @return la duree de la Section
     */
    public LocalTime getDuree() {
        return duree;
    }

    /**
     * Met a jour la duree de la Section
     * @param duree duree de la Section
     */
    public void setDuree(LocalTime duree) {
        this.duree = duree;
    }

    /**
     * Renvoie la distance de la Section
     * @return la distance de la Section
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Met a jour la distance de la Section
     * @param distance distance de la Section
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Renvoie la Ligne utiliser par la Section
     * @return la Ligne utiliser par la Section
     */
    public Ligne getLigne() {
        return ligne;
    }

    /**
     * Met a jour la Ligne utiliser par la Section
     * @param ligne Ligne utiliser par la Section
     */
    public void setLigne(Ligne ligne) {
        this.ligne = ligne;
    }
}
