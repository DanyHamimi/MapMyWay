package fr.uparis.backapp.services;

import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Reseau;
import fr.uparis.backapp.model.lieu.Station;
import fr.uparis.backapp.model.section.Section;
import fr.uparis.backapp.model.section.SectionTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.uparis.backapp.model.Coordonnee.isCoordinate;
import static fr.uparis.backapp.utils.Calculator.itineraireFactory;
import static fr.uparis.backapp.utils.Utils.getSchedulesByLine;
import static fr.uparis.backapp.utils.Utils.searchIteneray;
import static fr.uparis.backapp.utils.constants.Constants.DELIMITER;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@Service
public class ItineraryService {

    private final Reseau reseau;

    @Autowired
    public ItineraryService() {
        this.reseau = Reseau.getInstance();
    }


    public List<Section[]> searchItenerary(String origin, String destination, String time) {
//        List<Section[]> l = new ArrayList<>();
//        if (isCoordinate(origin) || isCoordinate(destination)) {
//            if (isCoordinate(origin) && isCoordinate(destination)) {
//                Coordonnee originCoord = new Coordonnee(parseFloat(origin.split(",")[1]), parseFloat(origin.split(",")[0]));
//                Coordonnee destinationCoord = new Coordonnee(parseFloat(destination.split(",")[1]), parseFloat(destination.split(",")[0]));
//                l = itineraireFactory(originCoord, destinationCoord, LocalTime.of(parseInt(time.split(":")[0]), parseInt(time.split(":")[1])));
//            } else if (isCoordinate(origin)) {
//                Station destinationStation = reseau.getStation(destination);
//                Coordonnee originCoord = new Coordonnee(parseFloat(origin.split(",")[1]), parseFloat(origin.split(",")[0]));
//                l = itineraireFactory(originCoord, destinationStation.getLocalisation(), LocalTime.of(parseInt(time.split(":")[0]), parseInt(time.split(":")[1])));
//            } else if (isCoordinate(destination)) {
//                Station originStation = reseau.getStation(origin);
//                Coordonnee destinationCoord = new Coordonnee(parseFloat(destination.split(",")[1]), parseFloat(destination.split(",")[0]));
//                l = itineraireFactory(originStation.getLocalisation(), destinationCoord, LocalTime.of(parseInt(time.split(":")[0]), parseInt(time.split(":")[1])));
//            }
//        } else {
//            Station originStation = reseau.getStation(origin);
//            Station destinationStation = reseau.getStation(destination);
//            l = itineraireFactory(originStation.getLocalisation(), destinationStation.getLocalisation(), LocalTime.of(parseInt(time.split(":")[0]), parseInt(time.split(":")[1])));
//        }
//        return l;
        return searchIteneray(origin, destination, time);
    }

    public List<String> autocomplete(String prefix) {
        List<Station> stationSuggested = reseau.getStations().stream().filter(station -> station.getNomLieu().toLowerCase().startsWith(prefix)).toList();
        List<String> stationSuggestedNames = new ArrayList<>();
        stationSuggested.forEach(station -> stationSuggestedNames.add(station.getNomLieu() + DELIMITER + station.getLocalisation().getLongitude() + DELIMITER + station.getLocalisation().getLatitude()));
        return stationSuggestedNames;
    }

    public Map<String, List<LocalTime>> getStationSchedules(String stationName) {
        Station station = reseau.getStation(stationName);

        if (station == null)
            return new HashMap<>();
        // recupérer toutes les sections qui partent de cette station
        List<SectionTransport> sectionTransports = reseau.getSections().stream().filter(section -> section.isStationDepart(station)).toList();
        return getSchedulesByLine(sectionTransports);
    }

}
