package fr.uparis.backapp.web;

import fr.uparis.backapp.model.section.Section;
import fr.uparis.backapp.services.ItineraryService;
import fr.uparis.backapp.utils.Parser;
import fr.uparis.backapp.model.lieu.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class PagesController {
    private Parser parser = Parser.getInstance(); //TODO : Ne pas faire cela mais utiliser les données du réseau
    private ItineraryService iteneraryService;
    private List<Message> messageList = new ArrayList<>();

    @Autowired
    public PagesController(ItineraryService iteneraryService) {
        this.iteneraryService = iteneraryService;
    }

    @PostMapping("/msg")
    public String postMessage(@ModelAttribute Message newMessage) {
        messageList.add(newMessage);

        return "redirect:msg";
    }

    @GetMapping("/msg")
    public String getMessage(Model model) {

        model.addAttribute("msglist", messageList);
        model.addAttribute("newMessage", new Message());
        return "home";
    }


    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> getAutocompleteSuggestions(@RequestParam("term") String term) {
        return iteneraryService.autocomplete(term);
    }

    @ResponseBody
    @GetMapping("/iteneray")
    public List<Section[]> searchItenerary(@RequestParam("origin") String origin, @RequestParam("destination") String destination, @RequestParam("time") String time) {
        return iteneraryService.searchItenerary(origin, destination, time);
    }


}
