package fr.uparis.backapp.model;

import java.time.LocalTime;

public class Section {

    private Station stationDepart;
    private Station stationArrivee;
    private LocalTime duree; // maybe it will be changed to double (number of minutes)
    private double distance;
    private Ligne ligne;

    public Section(Station stationDepart, Station stationArrivee, LocalTime duree, double distance,Ligne ligne) {
        this.stationDepart = stationDepart;
        this.stationArrivee = stationArrivee;
        this.duree = duree;
        this.distance = distance;
        this.ligne = ligne;

    }

    public Station getStationDepart() {
        return stationDepart;
    }

    public void setStationDepart(Station stationDepart) {
        this.stationDepart = stationDepart;
    }

    public Station getStationArrivee() {
        return stationArrivee;
    }

    public void setStationArrivee(Station stationArrivee) {
        this.stationArrivee = stationArrivee;
    }

    public LocalTime getDuree() {
        return duree;
    }

    public void setDuree(LocalTime duree) {
        this.duree = duree;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Ligne getLigne() {
        return ligne;
    }

    public void setLigne(Ligne ligne) {
        this.ligne = ligne;
    }
}
