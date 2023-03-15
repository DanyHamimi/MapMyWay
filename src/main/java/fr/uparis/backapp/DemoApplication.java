package fr.uparis.backapp;

import fr.uparis.backapp.Utils.Parser;
import fr.uparis.backapp.model.Reseau;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Lanceur de l'application
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Lance l'application
     * @param args
     */
    public static void main(String[] args) {

        //SpringApplication.run(DemoApplication.class, args);

        Parser p = new Parser();
        Reseau r = new Reseau();
        p.lect_Net(r);

        r.printStations();
        r.printSections();

    }

}
