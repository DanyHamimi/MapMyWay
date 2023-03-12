package fr.uparis.backapp.model;

import java.util.List;

public class Station {

    private String nomStation;
    private Cordonnee localisation;
    private List<Ligne> correspondances;

    public Station(String nomStation, Cordonnee localisation, List<Ligne> correspondances) {
        this.nomStation = nomStation;
        this.localisation = localisation;
        this.correspondances = correspondances;
    }

    public String getNomStation() {
        return nomStation;
    }

    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    public Cordonnee getLocalisation() {
        return localisation;
    }

    public void setLocalisation(Cordonnee localisation) {
        this.localisation = localisation;
    }

    public List<Ligne> getCorrespondances() {
        return correspondances;
    }

    public void setCorrespondances(List<Ligne> correspondances) {
        this.correspondances = correspondances;
    }

    // TODO define isNearByOrigin and isNearByDestination
    // TODO discuss addTime function
}
