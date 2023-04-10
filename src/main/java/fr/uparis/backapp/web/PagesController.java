package fr.uparis.backapp.web;

import fr.uparis.backapp.utils.Parser;
import fr.uparis.backapp.model.Station;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class PagesController {
    private Parser parser = Parser.getInstance(); //TODO : Ne pas faire cela mais utiliser les données du réseau
    private List<Message> messageList = new ArrayList<>();

    @PostMapping("/msg")
    public String postMessage(@ModelAttribute Message newMessage){
        messageList.add(newMessage);

        return "redirect:msg";
    }
    @GetMapping("/msg")
    public String getMessage(Model model) {

        model.addAttribute("msglist",messageList);
        model.addAttribute("newMessage", new Message());
        return "home";
    }


    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> getAutocompleteSuggestions(@RequestParam("term") String term) {
        // Create an ArrayList from parser station set names and coordinates, and return it after checking if the name is already in the list
        List<String> values = new ArrayList<>();
        List<String> valuesWithoutDuplicates = new ArrayList<>();

        for(Station station: parser.getStations()){
            String stationNameAndCoordinates = station.getNomStation() + ";" + station.getLocalisation().getLatitude() + ";" + station.getLocalisation().getLongitude();
            if (!valuesWithoutDuplicates.contains(stationNameAndCoordinates.split(";")[0])) {
                valuesWithoutDuplicates.add(stationNameAndCoordinates.split(";")[0]);
                values.add(stationNameAndCoordinates);
            }
        }

        return values.stream()
                .filter(value -> value.toLowerCase().split(";")[0].startsWith(term.toLowerCase()))
                .collect(Collectors.toList());
    }


}
