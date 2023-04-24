package fr.uparis.backapp.services;

import fr.uparis.backapp.model.Reseau;
import fr.uparis.backapp.model.lieu.Station;
import fr.uparis.backapp.model.section.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static fr.uparis.backapp.utils.Calculator.itineraire;
import static java.lang.Integer.parseInt;

@Service
public class ItineraryService {

    private final Reseau reseau;

    @Autowired
    public ItineraryService() {
        this.reseau = Reseau.getInstance();
    }

    public List<Section[]> searchItenerary(String origin, String destination, String time) {
        Station originStation = reseau.getStation(origin);
        Station destinationStation = reseau.getStation(destination);
        System.out.println( parseInt(time.split(":")[0]) +" "+  parseInt(time.split(":")[1]) );
        List<Section[]> l = itineraire(originStation, destinationStation, LocalTime.of(parseInt(time.split(":")[0]), parseInt(time.split(":")[1])));
        return l;
    }

    public List<String> autocomplete(String prefix) {
        List<Station> stationSuggested = reseau.getStations().stream().filter(station -> station.getNomLieu().toLowerCase().startsWith(prefix)).toList();
        List<String> stationSuggestedNames = new ArrayList<>();
        stationSuggested.forEach(station -> stationSuggestedNames.add(station.getNomLieu() + ";" + station.getLocalisation().getLatitude() + ";" + station.getLocalisation().getLongitude()));
        return stationSuggestedNames;
    }


}
