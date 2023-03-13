package fr.uparis.backapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represente un Reseau de transport
 */
public class Reseau {


    //lien vers le patern singleton ===> https://fr.wikipedia.org/wiki/Singleton_(patron_de_conception)
    private static Reseau instance;
    private List<Station> stations;
    private List<Section> sections;


    /**
     * Constructeur de la classe Reseau
     */
    private Reseau(){
        throw new IllegalStateException("Instance already created");
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
}
