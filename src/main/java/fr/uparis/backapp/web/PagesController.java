package fr.uparis.backapp.web;

import fr.uparis.backapp.model.section.Section;
import fr.uparis.backapp.services.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;


@RestController
public class PagesController {
    private final ItineraryService iteneraryService;

    @Autowired
    public PagesController(ItineraryService iteneraryService) {
        this.iteneraryService = iteneraryService;
    }

    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> getAutocompleteSuggestions(@RequestParam("term") String term) {
        return iteneraryService.autocomplete(term);
    }

    @ResponseBody
    @GetMapping("/iteneray")
    public List<Section[]> searchItenerary(@RequestParam("origin") String origin, @RequestParam("destination") String destination, @RequestParam("time") String time) {
        return iteneraryService.searchItinerary(origin, destination, time);
    }

    @ResponseBody
    @GetMapping("/schedules")
    public Map<String, List<LocalTime>> getStationSchedules(@RequestParam("station") String station) {
        return iteneraryService.getStationSchedules(station);
    }


}
