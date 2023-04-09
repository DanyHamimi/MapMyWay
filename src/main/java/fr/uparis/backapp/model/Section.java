package fr.uparis.backapp.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente une Section du Reseau de transport
 */
public class Section {
    final private Station stationDepart;
    final private Station stationArrivee;
    final private Duration duree;
    final private double distance;
    final private Ligne ligne;
    final private Set<LocalTime> horairesDepart;

    /**
     * Constructeur de la Section.
     * @param stationDepart Station de depart de la Section.
     * @param stationArrivee Station d'arrivée de la Section.
     * @param duree durée estimée de la Section.
     * @param distance distance de la Section.
     * @param ligne Ligne de la Section.
     */
    public Section(Station stationDepart, Station stationArrivee, Duration duree, double distance, Ligne ligne) {
        if(stationDepart.equals(stationArrivee)) throw new IllegalArgumentException();
        this.stationDepart = stationDepart;
        this.stationArrivee = stationArrivee;
        this.duree = duree;
        this.distance = distance;
        this.ligne = ligne;
        this.horairesDepart = new HashSet<>();
    }

    /**
     * Renvoie la Station de départ de la Section.
     * @return la Station de départ de la Section.
     */
    public Station getStationDepart() {
        return stationDepart;
    }

    /**
     * Renvoie la Station d'arrivée de la Section.
     * @return la Station d'arrivée de la Section.
     */
    public Station getStationArrivee() {
        return stationArrivee;
    }

    /**
     * Renvoie la durée de la Section.
     * @return la durée de la Section.
     */
    public Duration getDuree() {
        return duree;
    }

    /**
     * Renvoie la distance de la Section.
     * @return la distance de la Section.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Renvoie la Ligne utilisée par la Section.
     * @return la Ligne utilisée par la Section.
     */
    public Ligne getLigne() {
        return ligne;
    }

    /**
     * Renvoie les heures de départ de la Section.
     * @return les heures de départ de la Section.
     */
    public Set<LocalTime> getHorairesDepart() {
        return horairesDepart;
    }

    /**
     * Ajout d'un horaire de départ à la Section, si elle n'y est pas déjà.
     * @param horaire l'horaire de départ à ajouter à la Section.
     */
    public void addHoraireDepart(LocalTime horaire) {
        this.horairesDepart.add(horaire);
    }

    /**
     * Suppression d'un horaire de départ de la Section, si elle existe.
     * @param horaire l'horaire de départ à enlever de la Section.
     */
    public void removeHoraireDepart(LocalTime horaire) {
        this.horairesDepart.remove(horaire);
    }

    /**
     * Comparaison de deux Section.
     * @param o objet avec lequel comparer.
     * @return true si o et this ont les mêmes stations de départ et d'arrivée.
     */
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Section section = (Section)o;
        return section.getStationDepart().equals(this.getStationDepart()) && section.getStationArrivee().equals(this.getStationArrivee());
    }

    /**
     * Retourne une valeur de code de hachage pour Section.
     * @return la valeur de code de hachage pour Section.
     */
    @Override
    public int hashCode() {
        int result = stationDepart != null ? stationDepart.hashCode() : 0;
        result = 31 * result + (stationArrivee != null ? stationArrivee.hashCode() : 0);
        result = 31 * result + (ligne != null ? ligne.hashCode() : 0);
        return result;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères d'un objet Coordonnee.
     * @return la représentation sous forme de chaîne de caractères d'un objet Coordonnee.
     */
    @Override
    public String toString() {
        String s = this.getLigne().getNomLigne() + " : ";
        s += this.stationDepart.getNomStation() + " -> " + this.stationArrivee.getNomStation();
        s += " (durée = " + this.duree.toString() + ", distance = " + this.distance + " km)";
        for(LocalTime time: this.horairesDepart) s += "\n    " + time;
        return s;
    }

    /**
     * Regarde si la Section démarre d'une certaine station.
     * @param currentStation station de départ.
     * @return l'égalité entre la station donnée et celle de départ de la Section.
     */
    public boolean isFrom(Station currentStation) {
        return currentStation.equals(this.stationDepart);
    }

    /**
     * Regarde si la Section va jusqu'à une certaine station.
     * @param nextStation station d'arrivée.
     * @return l'égalité entre la station donnée et celle d'arrivée de la Section.
     */
    public boolean isGoingTo(Station nextStation) {
        return nextStation.equals(this.stationArrivee);
    }

    /**
     * Regarde si la Section va d'une certaine station à une autre.
     * @param currentStation station de départ.
     * @param nextStation station d'arrivée.
     * @return l'égalité entre les stations données et celles de la Section.
     */
    public boolean isGoingFromTo(Station currentStation, Station nextStation){
        return isFrom(currentStation) && isGoingTo(nextStation);
    }
}
