package fr.uparis.backapp.model;

import java.util.*;

/**
 * Represente une Station du Reseau de transport
 */
public class Station {
    private String nomStation;
    private Coordonnee localisation;
    private Set<Section> correspondances;

    /**
     * Constructeur de la classe Station
     *
     * @param nomStation      nom de la Station
     * @param localisation    Coordonnee de la Station
     * @param correspondances liste des correspondances avec d'autres Ligne du Reseau
     */
    public Station(String nomStation, Coordonnee localisation, Set<Section> correspondances) {
        this.nomStation = nomStation;
        this.localisation = localisation;
        this.correspondances = Objects.requireNonNullElseGet(correspondances, HashSet::new);
    }

    /**
     * Renvoie le nom de la Station
     *
     * @return le nom de la Station
     */
    public String getNomStation() {
        return nomStation;
    }

    /**
     * Met a jour le nom de la Station
     *
     * @param nomStation nom de la Station
     */
    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    /**
     * Renvoie la Coordonnee de la Station
     *
     * @return la Coordonne de la Station
     */
    public Coordonnee getLocalisation() {
        return localisation;
    }

    /**
     * Met a jour la Coordonnee de la Station
     *
     * @param localisation Coordonne de la Station
     */
    public void setLocalisation(Coordonnee localisation) {
        this.localisation = localisation;
    }

    /**
     * Renvoie les correspondances avec les Ligne du Reseau
     *
     * @return les correspondances avec les Ligne du Reseau
     */
    public Set<Section> getCorrespondances() {
        return correspondances;
    }

    /**
     * Met a jour les correspondances avec les Ligne du Reseau
     *
     * @param correspondances correspondances avec les Ligne du Reseau
     */
    public void setCorrespondances(Set<Section> correspondances) {
        this.correspondances = correspondances;
    }

    /**
     * Ajout d'une correspondance (càd une ligne) à la station
     *
     * @param  section une nouvelle correspondance possible à la station courante
     */
    public void addCorrespondance(Section section) {
        this.correspondances.add(section);
    }

    /**
     * Comparaison de deux stations.
     *
     * @param o objet avec lequel comparer
     * @return true si et seulement si o et this représentent la même station,
     * donc ont le même nom de station (unicité des noms de station)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return ((Station) o).getNomStation().equals(this.nomStation); //unicité des noms de station
    }

    /**
     * Retourne une valeur de code de hachage pour la station.
     *
     * @return la valeur de code de hachage pour la station
     */
    @Override
    public int hashCode() {
        int result = nomStation.hashCode();
        result = 31 * result + localisation.hashCode();
//        result = 31 * result + (correspondances != null ? correspondances.hashCode() : 0);
        return result;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères d'un objet Station.
     *
     * @return la représentation sous forme de chaîne de caractères d'un objet Station
     */
    @Override
    public String toString() {
        return nomStation;
    }

    // TODO discuss addTime function
}
