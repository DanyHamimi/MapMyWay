package fr.uparis.backapp.model;

import java.util.*;

/**
 * Représente une Station du Reseau de transport
 */
public class Station {
    final private String nomStation;
    final private Coordonnee localisation;
    final private Set<Section> correspondances;

    /**
     * Constructeur de la classe Station à partir de tous les attributs.
     * @param nomStation nom de la Station.
     * @param localisation Coordonnee de la Station.
     * @param correspondances liste des correspondances avec d'autres Ligne du Reseau.
     */
    public Station(String nomStation, Coordonnee localisation, Set<Section> correspondances) {
        this.nomStation = nomStation;
        this.localisation = localisation;
        this.correspondances = correspondances;
    }

    /**
     * Constructeur de la classe Station à partir du nom de la station et de sa localisation.
     * @param nomStation nom de la Station.
     * @param localisation Coordonnee de la Station.
     */
    public Station(String nomStation, Coordonnee localisation) {
        this(nomStation, localisation, new HashSet<>());
    }

    /**
     * Renvoie le nom de la Station.
     * @return le nom de la Station.
     */
    public String getNomStation() {
        return nomStation;
    }

    /**
     * Renvoie la Coordonnee de la Station.
     * @return la Coordonne de la Station.
     */
    public Coordonnee getLocalisation() {
        return localisation;
    }

    /**
     * Renvoie les correspondances avec les Ligne du Reseau.
     * @return les correspondances avec les Ligne du Reseau.
     */
    public Set<Section> getCorrespondances() {
        return correspondances;
    }

    /**
     * Ajout d'une correspondance à la Station, si elle n'y est pas déjà.
     * @param section une nouvelle correspondance possible à la station courante.
     */
    public void addCorrespondance(Section section) {
        if(section.getStationDepart().equals(this) || section.getStationArrivee().equals(this))
            this.correspondances.add(section);
    }

    /**
     * Suppression d'une correspondance de la Station, si elle existe.
     * @param section une ancienne correspondance possible depuis la station courante.
     */
    public void removeCorrespondance(Section section) {
        this.correspondances.remove(section);
    }

    /**
     * Comparaison de deux stations.
     * @param o objet avec lequel comparer.
     * @return true si et seulement si o et this représentent la même station,
     * donc ont le même nom de station (unicité des noms de station).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station)o;
        return station.getNomStation().equals(this.nomStation)
            && station.getLocalisation().equals(this.localisation); //unicité des noms de station
    }

    /**
     * Retourne une valeur de code de hachage pour la station.
     * @return la valeur de code de hachage pour la station.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + nomStation.hashCode();
        result = 31 * result + localisation.hashCode();
        return result;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères d'un objet Station.
     * @return la représentation sous forme de chaîne de caractères d'un objet Station.
     */
    @Override
    public String toString() {
        String s = nomStation + " (" + this.localisation + ") -> ";
        for(Section section: correspondances) s += section.getLigne().getNomLigne() + " ";
        return s;
    }

    // TODO discuss addTime function
}