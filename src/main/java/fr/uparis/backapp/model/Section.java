package fr.uparis.backapp.model;

import java.time.LocalTime;
import java.util.List;

/**
 * Represente une Section du Reseau de transport
 */
public class Section {
    private Station stationDepart;
    private Station stationArrivee;
    private LocalTime duree; // maybe it will be changed to double (number of minutes)
    private double distance;
    private Ligne ligne;
    private List<LocalTime> heuresDeparts;

    /**
     * Constructeur de la Section
     * @param stationDepart Station de depart de la Section
     * @param stationArrivee Station d'arrivee de la Section
     * @param duree duree estimee de la Section
     * @param distance distance de la Section
     * @param ligne Ligne de la Section
     */
    public Section(Station stationDepart, Station stationArrivee, LocalTime duree, double distance,Ligne ligne) {
        this.stationDepart = stationDepart;
        this.stationArrivee = stationArrivee;
        this.duree = duree;
        this.distance = distance;
        this.ligne = ligne;

    }
    public Section(Station stationDepart, Station stationArrivee, LocalTime duree,Ligne ligne) {
        this.stationDepart = stationDepart;
        this.stationArrivee = stationArrivee;
        this.duree = duree;
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

    /**
     * Renvoie l'heure a laquelle le train part
     * @return l'heure a laquelle le train part
     */
    public List<LocalTime> getHoraire() {
        return heuresDeparts;
    }

    /**
     * Comparaison de deux sections.
     * @param o objet avec lequel comparer
     * @return true si o et this ont les mêmes stations
     */
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Section section = (Section)o;
        return (section.getStationDepart().equals(this.getStationDepart()) && section.getStationArrivee().equals(this.getStationArrivee()));
    }

    /**
     * Renvoie true si la section vas d'une station a l'autre.
     * @param currentStation Station de départ
     * @param futureStation Station d'arrivée
     */
    public boolean goFromTo(Station currentStation, Station futureStation) {
        if (currentStation.equals(this.stationDepart) && futureStation.equals(this.stationArrivee)){
            return true;
        }
        return false;
    }
}
