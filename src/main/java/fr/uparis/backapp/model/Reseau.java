package fr.uparis.backapp.model;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;

/**
 * Represente un Reseau de transport
 */
public class Reseau {


    //lien vers le patern singleton ===> https://fr.wikipedia.org/wiki/Singleton_(patron_de_conception)
    private static Reseau instance;
    private List<Station> stations = new ArrayList<>();
    private List<Section> sections = new ArrayList<>();


    /**
     * Constructeur de la classe Reseau
     */
    public Reseau(){
        //throw new IllegalStateException("Instance already created");
    }

    /**
     * Renvoie l'instance de la classe
     * @return l'instance de la classe
     */
    public static Reseau getInstance(){
        if(instance == null){
            // TODO instancier l'attribut instance
        }
        return instance;
    }

    /**
     * Renvoie la liste des Stations en fonction de leur origine
     * @return la liste des Stations en fonction de leur origine
     */
    public List<Station> getNearStationsByOrigin(){
        //TODO define the function
        return new ArrayList<>();
    }

    /**
     * Renvoie la liste des Stations en fonction de leur destination
     * @return la liste des Stations en fonction de leur destination
     */
    public List<Station> getNearByDestination(){
        //TODO define the function
        return new ArrayList<>();
    }

    /**
     * Determine le plus court chemin pour se rendre a une Coordonnee a une autre en connaissant le Reseau
     * @return la liste des plus courts chemins
     */
    public List<Section> djikstra(){
        //TODO define the function
        return null;
    }

    public void addStation(Station station){
        for(Station s : stations){
            if(s.getNomStation().equals(station.getNomStation())){
                return;
            }
        }
        this.stations.add(station);
    }

    public void addSection(Section section){
        this.sections.add(section);
    }

    public void printStations(){
        for(Station station : stations){
            System.out.println(station.getNomStation());
            if(station.getCorrespondances().size() > 1){
                System.out.println("Correspondances : " + station.getCorrespondances().get(0).getNomLigne() + " " + station.getCorrespondances().get(1).getNomLigne());
            }
        }
    }

    public Station getStationByName(String value) {
        for(Station station : stations){
            if(station.getNomStation().equals(value)){
                return station;
            }
        }
        System.out.println("Station " + value + " not found");
        return null;
    }
    public boolean isStationExist(String value){
        for(Station station : stations){
            if(station.getNomStation().equals(value)){
                return true;
            }
        }
        return false;
    }

    public void printSections() {
        for(Section section : sections){
            System.out.println(section.getStationDepart().getNomStation() + " -> " + section.getStationArrivee().getNomStation() + " Distance " + section.getDistance() + "km" + " Temps " + section.getDuree() + "min, LIGNE : " + section.getLigne().getNomLigne() );
        }
    }
}
