package fr.uparis.backapp.model;

import java.time.LocalTime;
import java.util.List;

public class Ligne {

    private String nomLigne;
    private List<Station> stations;
    private List<LocalTime> tempsDeparts;

    public Ligne(String nomLigne, List<Station> stations, List<LocalTime> tempsDeparts) {
        this.nomLigne = nomLigne;
        this.stations = stations;
        this.tempsDeparts = tempsDeparts;
    }

    public String getNomLigne() {
        return nomLigne;
    }

    public void setNomLigne(String nomLigne) {
        this.nomLigne = nomLigne;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public List<LocalTime> getTempsDeparts() {
        return tempsDeparts;
    }

    public void setTempsDeparts(List<LocalTime> tempsDeparts) {
        this.tempsDeparts = tempsDeparts;
    }

}
