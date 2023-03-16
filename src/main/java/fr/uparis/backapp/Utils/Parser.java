package fr.uparis.backapp.Utils;
import fr.uparis.backapp.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Permet de parser les donnees du client et de creer nos Objets.
 */
public class Parser {
    private final int nbElem = 7;
    String filename = "src/Metro91.csv";
    /**
     * Lecture du Reseau.
     * lit la description d’un reseau de transport arbitraire.
     */
    public void lect_Net(Reseau r){
        int amount_of_sections = 0;
        String line;
        List<String> nameLignes = new ArrayList<>();
        List<Ligne> listLignes = new ArrayList<>();
        List<String> lignesCSV = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                amount_of_sections++;
                lignesCSV.add(line);
                String[] values = line.split(";");
                if(!nameLignes.contains(values[4])){
                    nameLignes.add(values[4]);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        List<String> Sections = new ArrayList<>();
        for(int i = 0; i < nameLignes.size(); i++){
            List<Station> listStations = new ArrayList<>();
            for(int j = 0; j < lignesCSV.size(); j++){
                String[] values = lignesCSV.get(j).split(";");
                if(values[4].equals(nameLignes.get(i))){
                    Coordonnee coordonneeDepart = new Coordonnee(Double.parseDouble(values[1].split(",")[0]), Double.parseDouble(values[1].split(",")[1]));
                    if(!r.isStationExist(values[0])){
                        Station s = new Station(values[0], coordonneeDepart, null);
                        listStations.add(s);
                        r.addStation(s);
                    }
                    else{
                        if(!listStations.contains(r.getStationByName(values[0]))){
                            listStations.add(r.getStationByName(values[0]));
                        }
                    }
                    if(!r.isStationExist(values[2])){
                        Coordonnee coordonneeArrivee = new Coordonnee(Double.parseDouble(values[3].split(",")[0]), Double.parseDouble(values[3].split(",")[1]));
                        Station s = new Station(values[2], coordonneeArrivee, null);
                        listStations.add(s);
                        r.addStation(s);
                    }
                    else {
                        if(!listStations.contains(r.getStationByName(values[2]))){
                            listStations.add(r.getStationByName(values[2]));
                        }
                    }
                }
            }
            listLignes.add(new Ligne(nameLignes.get(i), listStations,null));
        }

        for(int i = 0; i < listLignes.size(); i++){
            for(int j = 0; j < listLignes.get(i).getStations().size(); j++){
                listLignes.get(i).getStations().get(j).addCorrespondance(listLignes.get(i));
            }
        }

        for(int i = 0 ; i < lignesCSV.size(); i++){
            String[] values = lignesCSV.get(i).split(";");
            String correctLength = values[6].replace(";" , "");
            LocalTime time = LocalTime.parse(correctTime(values[5]));
            Section s = new Section(r.getStationByName(values[0]), r.getStationByName(values[2]), time, Double.parseDouble(correctLength), r.getStationByName(values[0]).getLigneByNom(values[4]));
            r.addSection(s);
        }
    }

    public String correctTime(String time){
        //Make a string correct time with the format hour : minute : second
        String correctedTime = String.format("%1$02d:%2$02d:%3$02d",
                               0,
                               Integer.parseInt(time.split(":")[0]),
                               Integer.parseInt(time.split(":")[1]));
        return correctedTime;
    }

    /**
     * Lecture des horaires.
     * Affiche les horaires de passage d’un transport a une Station donnee.
     */
    public void lect_Time(){}
}