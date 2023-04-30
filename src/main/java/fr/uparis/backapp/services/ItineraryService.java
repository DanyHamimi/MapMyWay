package fr.uparis.backapp.services;

import fr.uparis.backapp.exceptions.StationNotFoundException;
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

import static fr.uparis.backapp.utils.Calculator.itineraireFactory;
import static fr.uparis.backapp.utils.Utils.*;
import static fr.uparis.backapp.utils.constants.Constants.DELIMITER;

@Service
public class ItineraryService {

    private final Reseau reseau;

    @Autowired
    public ItineraryService() {
        this.reseau = Reseau.getInstance();
    }


    /**
     * Recherche un itinéraire entre deux lieux spécifiés à un moment donné.
     *
     * @param origin      la station ou les coordonnées de départ.
     * @param destination la station ou les coordonnées d'arrivée.
     * @param time        l'heure de départ.
     * @return la liste des itinéraires possibles sous forme de tableau de sections.
     */
    public List<Section[]> searchItinerary(String origin, String destination, String time) {
        List<Section[]> trajects;
        LocalTime trajectTime = getTimeFromString(time);
        try {
            Coordonnee originCoordinates = fetchCoordinates(origin);
            Coordonnee destinationCoordinates = fetchCoordinates(destination);
            trajects = itineraireFactory(originCoordinates, destinationCoordinates, trajectTime);
        } catch (StationNotFoundException e) {
            trajects = new ArrayList<>();
        }
        return trajects;
    }

    /**
     * L'autocomplétion de la saisie dans la barre de recherche de stations.
     *
     * @param prefix le préfixe de la station recherchée.
     * @return la liste des stations qui ont le préfixe demandé.
     */
    public List<String> autocomplete(String prefix) {
        List<Station> stationSuggested = reseau.getStations().stream().filter(station -> station.getNomLieu().toLowerCase().startsWith(prefix)).toList();
        List<String> stationSuggestedNames = new ArrayList<>();
        stationSuggested.forEach(station -> stationSuggestedNames.add(station.getNomLieu() + DELIMITER + station.getLocalisation().getLongitude() + DELIMITER + station.getLocalisation().getLatitude()));
        return stationSuggestedNames;
    }

    /**
     * Retourne tous les horaires de passage des trains pour une station donnée.
     *
     * @param stationName la station pour laquelle on cherche les horaires de passage.
     * @return les horaires de passage des trains, avec la direction correspondante.
     */
    public Map<String, List<LocalTime>> getStationSchedules(String stationName) {
        Station station = reseau.getStation(stationName);

        if (station == null)
            return new HashMap<>();
        // recupérer toutes les sections qui partent de cette station
        List<SectionTransport> sectionTransports = reseau.getSections().stream().filter(section -> section.isStationDepart(station)).toList();
        return getSchedulesByLine(sectionTransports);
    }

}
