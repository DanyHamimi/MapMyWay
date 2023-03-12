package fr.uparis.backapp.model;

import java.util.ArrayList;
import java.util.List;

public class Reseau {


    //lien vers le patern singleton ===> https://fr.wikipedia.org/wiki/Singleton_(patron_de_conception)
    private static Reseau instance;
    private List<Station> stations;
    private List<Section> sections;


    private Reseau(){
        throw new IllegalStateException("Instance already created");
    }
    public static Reseau getInstance(){
        if(instance == null){
            // TODO instancier l'attribut instance
        }
        return instance;
    }

    public List<Station> getNearStationsByOrigin(){
        //TODO define the function
        return new ArrayList<>();
    }

    public List<Station> getNearByDestination(){
        //TODO define the function
        return new ArrayList<>();
    }

    public List<Section> djikstra(){
        //TODO define the function
        return null;
    }
}
