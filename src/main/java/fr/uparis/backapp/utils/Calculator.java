package fr.uparis.backapp.utils;

import fr.uparis.backapp.model.*;
import fr.uparis.backapp.model.lieu.Lieu;
import fr.uparis.backapp.model.lieu.Station;
import fr.uparis.backapp.model.section.Section;
import fr.uparis.backapp.model.section.SectionTransport;
import fr.uparis.backapp.utils.constants.Constants;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

import static fr.uparis.backapp.utils.Utils.*;

/**
 * Classe static pour calculer des itinéraires en fonction de différents paramètres.
 */
public class Calculator {
    /**
     * Calcule un itinéraire totalement à pied, à partir de coordonnées.
     *
     * @param origine point de départ du trajet.
     * @param destination point d'arrivée du trajet.
     * @param horaireDepart horaire de départ.
     * @return une section contenant toutes les informations du trajet à pied.
     */
    public static Section walkingItineraire(Coordonnee origine, Coordonnee destination, LocalTime horaireDepart) {
        double distance = distanceBetween(origine, destination);
        Duration duree = walkingDurationOf(distance);
        return new Section(new Lieu("Départ", origine, horaireDepart),
                           new Lieu("Arrivée", destination, horaireDepart.plus(duree)),
                           duree,
                           distance);
    }

    /**
     * Calcule un itinéraire totalement à pied, à partir de Lieux.
     *
     * @param origine la station de départ du trajet.
     * @param destination la station d'arrivée du trajet.
     * @param horaireDepart horaire de départ.
     * @return une section contenant toutes les informations du trajet à pied.
     */
    public static Section walkingItineraire(Lieu origine, Lieu destination, LocalTime horaireDepart) {
        return walkingItineraire(origine.getLocalisation(), destination.getLocalisation(), horaireDepart);
    }

    /**
     * Calcule un itinéraire, avec correspondances et horaires, depuis une Coordonnee à une autre,
     * en donnant la possibilité de fixer un périmètre manuellement de recherche pour les stations en termes de distance.
     *
     * @param origine point de départ.
     * @param destination point d'arrivée.
     * @param horaireDepart l'horaire de départ.
     * @param minDistance distance minimale du périmètre de recherche pour les stations proches, ou -1 par défaut.
     * @param maxDistance distance maximale du périmètre de recherche pour les stations proches, ou -1 par défaut.
     * @return les 5 trajets les plus courts sous forme de liste de Section.
     */
    public static List<Section[]> itineraire(Coordonnee origine, Coordonnee destination, LocalTime horaireDepart, double minDistance, double maxDistance) {
        if(minDistance > maxDistance) maxDistance = minDistance + Constants.DEFAULT_ECART_DISTANCE;

        //Les 5 trajets les plus optimaux à retourner, avec celui à pied en termes de comparatif/pire trajet
        List<Section[]> trajetsSaved = new ArrayList<>();
        trajetsSaved.add(new Section[]{walkingItineraire(origine, destination, horaireDepart)});
        LocalTime maxTime = trajetsSaved.get(0)[0].getArrivee().getHoraireDePassage();

        //Les stations les plus proches de l'origine et de la destination, avec un minimum de marche au début
        List<Station> procheOrigineDeb = getNearStations(origine, minDistance, maxDistance);
        List<Station> procheDestinationDeb = getNearStations(destination, -1, maxDistance);

        //Les stations les plus proches de l'origine et de la destination, avec un minimum de marche au début et à la fin
        List<Station> procheOrigineMid = getNearStations(origine, minDistance / 2, maxDistance);
        List<Station> procheDestinationMid = getNearStations(destination, minDistance / 2, maxDistance);

        //Les stations les plus proches de l'origine et de la destination, avec un minimum de marche à la fin
        List<Station> procheOrigineFin = getNearStations(origine, -1, maxDistance);
        List<Station> procheDestinationFin = getNearStations(destination, minDistance, maxDistance);

        //Initialise les couples de stations déjà traités par dijkstra
        HashMap<Station, List<Station>> coupleStations = new HashMap<>();
        addStationsAndListToMap(coupleStations, procheOrigineDeb);
        addStationsAndListToMap(coupleStations, procheOrigineMid);
        addStationsAndListToMap(coupleStations, procheOrigineFin);

        //Trouve le plus court chemin pour chaque couple procheOrigine-procheDestination
        addTrajetsOptimaux(origine, destination, horaireDepart, trajetsSaved, coupleStations, procheOrigineDeb, procheDestinationDeb, maxTime);
        addTrajetsOptimaux(origine, destination, horaireDepart, trajetsSaved, coupleStations, procheOrigineMid, procheDestinationMid, maxTime);
        addTrajetsOptimaux(origine, destination, horaireDepart, trajetsSaved, coupleStations, procheOrigineFin, procheDestinationFin, maxTime);

        return trajetsSaved;
    }

    /**
     * Calcule un itinéraire, avec correspondances et horaires, depuis une Coordonnee à une autre,
     * en donnant la possibilité de fixer manuellement un périmètre de recherche pour les stations en termes de durée.
     *
     * @param origine point de départ.
     * @param destination point d'arrivée.
     * @param horaireDepart l'horaire de départ.
     * @param minTemps durée de marche minimale du périmètre de recherche pour les stations proches.
     * @return les 5 trajets les plus courts sous forme de liste de Section.
     */
    public static List<Section[]> itineraire(Coordonnee origine, Coordonnee destination, LocalTime horaireDepart, Duration minTemps) {
        return itineraire(origine, destination, horaireDepart, distanceOfWalkingDuration(minTemps), -1);
    }

    /**
     * Calcule un itinéraire, avec correspondances et horaires, depuis une Coordonnee à une autre,
     * en fixant un périmètre de recherche des stations par défaut.
     *
     * @param origine point de départ.
     * @param destination point d'arrivée.
     * @param horaireDepart l'horaire de départ.
     * @return les 5 trajets les plus courts sous forme de liste de Section.
     */
    public static List<Section[]> itineraire(Coordonnee origine, Coordonnee destination, LocalTime horaireDepart) {
        //Les 5 trajets les plus optimaux à retourner, avec celui à pied en termes de comparatif/pire trajet
        List<Section[]> trajetsSaved = new ArrayList<>();
        trajetsSaved.add(new Section[]{walkingItineraire(origine, destination, horaireDepart)});
        LocalTime maxTime = trajetsSaved.get(0)[0].getArrivee().getHoraireDePassage();

        //Trouve les stations les plus proches de l'origine et de la destination
        List<Station> procheOrigine = getNearStations(origine, -1, -1);
        List<Station> procheDestination = getNearStations(destination, -1, -1);

        //Initialise les couples de stations déjà traités par dijkstra
        HashMap<Station, List<Station>> coupleStations = new HashMap<>();
        addStationsAndListToMap(coupleStations, procheOrigine);

        //Trouve le plus court chemin pour chaque couple procheOrigine-procheDestination
        addTrajetsOptimaux(origine, destination, horaireDepart, trajetsSaved, coupleStations, procheOrigine, procheDestination, maxTime);

        return trajetsSaved;
    }

    /**
     * Calcule un itinéraire, avec correspondances et horaires, depuis une Station à une autre,
     * en donnant la possibilité de fixer un périmètre de recherche pour les stations manuellement.
     *
     * @param origine point de départ.
     * @param destination point d'arrivée.
     * @param horaireDepart l'horaire de départ.
     * @param minDistance distance minimale du périmètre de recherche pour les stations proches, ou -1 par défaut.
     * @param maxDistance distance maximale du périmètre de recherche pour les stations proches, ou -1 par défaut.
     * @return les 5 trajets les plus courts sous forme de liste de Section.
     */
    public static List<Section[]> itineraire(Station origine, Station destination, LocalTime horaireDepart, double minDistance, double maxDistance) {
        return itineraire(origine.getLocalisation(), destination.getLocalisation(), horaireDepart, minDistance, maxDistance);
    }

    /**
     * Calcule un itinéraire, avec correspondances et horaires, depuis une Station à une autre,
     * en donnant la possibilité de fixer manuellement un périmètre de recherche pour les stations en termes de durée.
     *
     * @param origine point de départ.
     * @param destination point d'arrivée.
     * @param horaireDepart l'horaire de départ.
     * @param minTemps durée de marche minimale du périmètre de recherche pour les stations proches.
     * @return les 5 trajets les plus courts sous forme de liste de Section.
     */
    public static List<Section[]> itineraire(Station origine, Station destination, LocalTime horaireDepart, Duration minTemps) {
        return itineraire(origine.getLocalisation(), destination.getLocalisation(), horaireDepart, minTemps);
    }

    /**
     * Calcule un itinéraire, avec correspondances et horaires, depuis une Station à une autre,
     * en fixant un périmètre de recherche des stations par défaut.
     *
     * @param origine point de départ.
     * @param destination point d'arrivée.
     * @param horaireDepart l'horaire de départ.
     * @return les 5 trajets les plus courts sous forme de liste de Section.
     */
    public static List<Section[]> itineraire(Station origine, Station destination, LocalTime horaireDepart) {
        return itineraire(origine.getLocalisation(), destination.getLocalisation(), horaireDepart);
    }

    /**
     * Cherche les stations proches d'une coordonnée.
     *
     * @param coordonnee coordonnée du point de départ.
     * @param maxDistance distance maximale acceptable entre deux points, en km.
     * @param minDistance distance minimale acceptable entre deux points, en km.
     * @return la liste des stations dont la distance est majorée par maxDistance et minorée par minDistance.
     */
    private static List<Station> getNearStations(Coordonnee coordonnee, double minDistance, double maxDistance) {
        if(minDistance < 0) minDistance = Constants.DEFAULT_MIN_DISTANCE;
        if(maxDistance < 0) maxDistance = Constants.DEFAULT_MAX_DISTANCE;
        List<Station> nearStations = new ArrayList<>();
        double distance;
        for(Station s: Reseau.getInstance().getStations()) {
            distance = distanceBetween(coordonnee, s.getLocalisation());
            if(distance >= minDistance && distance <= maxDistance) nearStations.add(s);
        }

        return nearStations;
    }

    /**
     * Ajoute les couples (Station, List<Station>) pour chaque station, avec la liste initialisée à la station en entrée.
     * Le but étant d'y ajouter les couples de stations déjà traités par dijkstra.
     *
     * @param coupleStations la HashMap dans laquelle on ajoute les couples.
     * @param procheOrigine les stations à ajouter dans la HashMap dans le format désiré.
     */
    private static void addStationsAndListToMap(HashMap<Station, List<Station>> coupleStations, List<Station> procheOrigine) {
        for(Station station: procheOrigine) {
            List<Station> list = new LinkedList<>();
            list.add(station);
            coupleStations.put(station, list);
        }
    }

    /**
     * Trouve le plus court chemin pour chaque couple procheOrigine-procheDestination, et l'ajoute dans les trajets sauvegardés.
     *
     * @param origine point de départ.
     * @param destination point d'arrivée.
     * @param horaireDepart l'horaire de départ.
     * @param trajetsSaved les trajets déjà sauvegardés par rapport à une précédente recherche.
     * @param coupleStations les couples de stations déjà traités par dijkstra.
     * @param procheOrigine les stations proches de l'origine.
     * @param procheDestination les stations proches de la destination.
     * @param maxTime le temps à ne pas dépasser pour être ajouté aux trajets sauvegardés.
     */
    private static void addTrajetsOptimaux(Coordonnee origine, Coordonnee destination, LocalTime horaireDepart, List<Section[]> trajetsSaved,
            HashMap<Station, List<Station>> coupleStations, List<Station> procheOrigine, List<Station> procheDestination, LocalTime maxTime) {
        for(Station origineCandidat: procheOrigine) {
            for(Station destinationCandidat: procheDestination) {
                List<Station> listStationsTraitees = coupleStations.get(origineCandidat);

                //Si la combinaison de stations n'est pas encore traitée
                if(!listStationsTraitees.contains(destinationCandidat)) {
                    //Calcul de la durée de marche avant d'arriver à la station candidate, puis récupère l'itinéraire avec dijkstra
                    double distanceDebut = distanceBetween(origine, origineCandidat.getLocalisation());
                    Duration dureeDebut = walkingDurationOf(distanceDebut);
                    List<SectionTransport> trajet = djikstra(origineCandidat, destinationCandidat, horaireDepart.plus(dureeDebut));

                    if(trajet != null){
                        //Horaire auquel on finit le trajet
                        double distanceFin = distanceBetween(destination, destinationCandidat.getLocalisation());
                        Duration dureeFin = walkingDurationOf(distanceFin);
                        LocalTime horaireArrivee = trajet.get(trajet.size()-1).getArrivee().getHoraireDePassage().plus(dureeFin);

                        //Vérifie si le trajet est actuellement parmi les 5 trajets les plus optimaux
                        if(horaireArrivee.isBefore(maxTime)) {
                            //Si c'est le cas, faire une copie du trajet à ajouter
                            Section sectionDebut = new Section(new Lieu("Départ", origine, horaireDepart), origineCandidat, dureeDebut, distanceDebut);
                            Section sectionFin = new Section(destinationCandidat, new Lieu("Arrivée", destination, horaireArrivee), dureeFin, distanceFin);
                            Section[] sectionToSave = createNewTrajet(trajet, sectionDebut, sectionFin);

                            //Et l'ajouter dans les trajets, dans l'ordre chronologique d'horaire d'arrivée
                            trajetsSaved.add(getInsertIndex(trajetsSaved, horaireArrivee), sectionToSave);

                            //Gérer la liste des trajets, pour que le nombre de résultats ne dépasse pas Constants.MAX_TRAJETS_NUMBER
                            if(trajetsSaved.size() > Constants.MAX_TRAJETS_NUMBER) {
                                trajetsSaved.remove(trajetsSaved.size() - 1);
                                Section[] dernierTrajet = trajetsSaved.get(trajetsSaved.size() - 1);
                                maxTime = dernierTrajet[dernierTrajet.length - 1].getArrivee().getHoraireDePassage();
                            }
                        }
                    }

                    //Ajoute la station aux couples de stations déjà traités
                    listStationsTraitees.add(destinationCandidat);
                }
            }
        }
    }

    /**
     * Détermine le plus court chemin pour se rendre d'une Station à une autre en connaissant le Reseau.
     * Les horaires des trains et les temps de correspondance sont pris en compte.
     *
     * @param stationDepart la station de départ.
     * @param stationArrivee la station d'arrivée.
     * @return le plus court chemin sous forme d'une liste de section, ou null si aucun chemin n'a été trouvé.
     */
    private static List<SectionTransport> djikstra(Station stationDepart, Station stationArrivee, LocalTime horaireDepart) {
        //Initialisation
        Map<Station, LocalTime> myMap = new HashMap<>();
        for(Station station: Reseau.getInstance().getStations())
            myMap.put(station, null); //null représente ici un temps infini
        myMap.put(stationDepart, horaireDepart);

        //Station, la meilleure liste de section pour y accéder
        Map<Station, List<SectionTransport>> trace = new Hashtable<>();
        trace.put(stationDepart, new LinkedList<>());

        //Prendre l'élément le plus petit.
        Station currentStation;
        LocalTime currentHoraire;
        while(!myMap.isEmpty() && (currentStation = min(myMap)) != null) {
            currentHoraire = myMap.get(currentStation);
            currentStation.setHoraireDePassage(currentHoraire);

            //Si c'est la destination, c'est gagné!
            if(currentStation.equals(stationArrivee)) return trace.get(currentStation);
            //Sinon, on examine les voisins
            else{
                List<SectionTransport> currentSectionTransports = trace.get(currentStation);
                for(SectionTransport sectionTransport: currentStation.getCorrespondances()) {
                    Station nextStation = sectionTransport.getArrivee();

                    //On ne veut que ceux qui sont encore dans myMap, et que le prochain train
                    if(myMap.containsKey(nextStation)) {
                        LocalTime prochainDepart = sectionTransport.getHoraireProchainDepart(currentHoraire);
                        if(prochainDepart != null) {
                            prochainDepart = prochainDepart.plus(sectionTransport.getDuree());

                            //Ajout du temps de correspondance
                            if(!currentSectionTransports.isEmpty()) {
                                Ligne previousLigne = currentSectionTransports.get(currentSectionTransports.size() - 1).getLigne();
                                if(previousLigne != sectionTransport.getLigne()){
                                    Coordonnee c1 = currentStation.getLocalisation(previousLigne.getNomLigne());
                                    Coordonnee c2 = currentStation.getLocalisation(sectionTransport.getLigne().getNomLigne());
                                    Duration duree = walkingDurationOf(distanceBetween(c1, c2));
                                    prochainDepart = prochainDepart.plus(duree);
                                }
                            }

                            //Mise à jour de l'horaire de départ et du trajet à suivre pour arriver à nextStation
                            LocalTime horaire = myMap.get(nextStation);
                            if(horaire == null || horaire.isAfter(prochainDepart)) myMap.put(nextStation, prochainDepart);

                            List<SectionTransport> sectionTransports = new LinkedList<>(currentSectionTransports);
                            sectionTransports.add(sectionTransport);
                            trace.put(nextStation, sectionTransports);
                        }
                    }
                }
            }
            myMap.remove(currentStation);
        }
        return null; //pas de trajet possible à cause de l'heure des transports
    }

    /**
     * Trouve l'élément de poids minimal du dictionnaire.
     *
     * @param myMap une map telle que celle utilisée dans l'algorithme de djikstra.
     * @return La station avec le plus petit LocalTime.
     */
    private static Station min(Map<Station, LocalTime> myMap) {
        Station stationMin = null;
        LocalTime tempsMin = null;

        Station stationTmp;
        LocalTime tempsTmp;
        for(Map.Entry<Station, LocalTime> entry: myMap.entrySet()) {
            stationTmp = entry.getKey();
            tempsTmp = entry.getValue();
            if(stationMin == null) {
                stationMin = stationTmp;
                tempsMin = tempsTmp;
            }
            else if(tempsMin == null || (tempsTmp != null&&tempsMin.isAfter(tempsTmp))) {
                stationMin = stationTmp;
                tempsMin = entry.getValue();
            }
        }
        return (tempsMin == null)? null : stationMin;
    }

    /**
     * Crée un nouveau trajet avec les copies des sections fournies, et en ajoutant les sections de début et de fin, qui sont à pied.
     *
     * @param trajet le trajet à copier.
     * @param sectionDebut la section de début, à pied.
     * @param sectionFin la section de fin, à pied.
     * @return une copie du trajet demandé, avec ajout du début et de la fin à pied.
     */
    private static Section[] createNewTrajet(List<SectionTransport> trajet, Section sectionDebut, Section sectionFin) {
        Section[] copied = new Section[trajet.size() + 2];
        int i = 0;

        copied[i++] = sectionDebut;
        for(SectionTransport section: trajet) copied[i++] = section.copy();
        copied[i] = sectionFin;

        return copied;
    }

    /**
     * Trouve l'index auquel insérer le nouvel horaire, en maintenant la liste des trajets dans l'ordre croissant des horaires d'arrivée.
     *
     * @param trajetsSaved la liste des trajets, trié dans l'ordre croissant des horaires d'arrivée.
     * @param horaireArrivee l'horaire d'arrivée du trajet à insérer dans la liste des trajets.
     * @return l'index d'insertion d'un trajet qui a pour horaire d'arrivée celui demandé.
     */
    private static int getInsertIndex(List<Section[]> trajetsSaved, LocalTime horaireArrivee) {
        int index = 0;
        Section[] trajet = trajetsSaved.get(index);
        while(horaireArrivee.isAfter(trajet[trajet.length - 1].getArrivee().getHoraireDePassage()))
            trajet = trajetsSaved.get(++index);
        return index;
    }
}