package fr.uparis.backapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represente une Station du Reseau de transport
 */
public class Station {

    private String nomStation;
    private Cordonnee localisation;
    private List<Ligne> correspondances;

    /**
     * Constructeur de la classe
     * @param nomStation nom de la Station
     * @param localisation Coordonnee de la Station
     * @param correspondances liste des correspondances avec d'autres Ligne du Reseau
     */
    public Station(String nomStation, Cordonnee localisation, List<Ligne> correspondances) {
        this.nomStation = nomStation;
        this.localisation = localisation;
        if(correspondances == null) {
            this.correspondances = new ArrayList<>();
        } else {
            this.correspondances = correspondances;
        }
    }

    /**
     * Renvoie le nom de la Station
     * @return le nom de la Station
     */
    public String getNomStation() {
        return nomStation;
    }

    /**
     * Met a jour le nom de la Station
     * @param nomStation nom de la Station
     */
    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    /**
     * Renvoie la Coordonnee de la Station
     * @return la Coordonne de la Station
     */
    public Cordonnee getLocalisation() {
        return localisation;
    }

    /**
     * Met a jour la Coordonnee de la Station
     * @param localisation Coordonne de la Station
     */
    public void setLocalisation(Cordonnee localisation) {
        this.localisation = localisation;
    }

    /**
     * Renvoie les correspondances avec les Ligne du Reseau
     * @return les correspondances avec les Ligne du Reseau
     */
    public List<Ligne> getCorrespondances() {
        return correspondances;
    }

    /**
     * Met a jour les correspondances avec les Ligne du Reseau
     * @param correspondances correspondances avec les Ligne du Reseau
     */
    public void setCorrespondances(List<Ligne> correspondances) {
        this.correspondances = correspondances;
    }

    public void addCorrespondance(Ligne ligne) {
        this.correspondances.add(ligne);
    }

    public String getname() {
        return nomStation;
    }

    public Ligne getLigneByNom(String nomLigne) {
        for(Ligne lignes : this.correspondances) {
            if(lignes.getNomLigne().equals(nomLigne)) {
                return lignes;
            }
        }
        return null;
    }

    // TODO define isNearByOrigin and isNearByDestination
    // TODO discuss addTime function
}
