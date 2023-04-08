package fr.uparis.backapp.utils;

import fr.uparis.backapp.utils.constants.Constants;
import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Reseau;
import fr.uparis.backapp.model.Section;
import fr.uparis.backapp.model.Station;

import java.util.List;

/**
 * Permet de calculer des itinéraires en fonction de différents paramètres
 */
public class Calculator {
    /**
     * Calcul un itinéraire depuis une Coordonnee à une autre.
     * @param origin Coordonnee d'origine.
     * @param destination Coordonnee de destination.
     */
    public static List<Section> itineraire(Coordonnee origin, Coordonnee destination) {
        //TODO: define method
        return null;
    }

    /**
     * Trajet dans Reseau.
     * Renvoie une séquence de transport, avec horaire, pour se rendre d'une station à une autre.
     * @param s1 Station de départ.
     * @param s2 Station d'arrivée.
     * @return une séquence de transport, avec horaire, pour se rendre de s1 à s2.
     */
    public static List<Section> plan0(Station s1, Station s2) {
        return itineraire(s1.getLocalisation(), s2.getLocalisation());
    }

    /**
     * Trajet optimise dans le Reseau.
     * Renvoie une séquence optimale en termes de temps comme de distance parcourue.
     * @param s1 Station de départ.
     * @param s2 Station d'arrivée.
     * @return une séquence optimale en termes de temps comme de distance parcourue, entre s1 et s2.
     */
    public static List<Section> plan1(Station s1, Station s2) {
        //TODO: define method
        return null;
    }


    /**
     * Trajet avec debut ou fin à pied.
     * Permet de partir et d'arriver à n'importe quelle Coordonnee géographique, et pas seulement depuis des stations du réseau.
     * @param c1 Coordonnee de départ.
     * @param c2 Coordonnee d'arrivée.
     */
    public static List<Section> plan2(Coordonnee c1, Coordonnee c2) {
        return itineraire(c1, c2);
    }

    /**
     * Trajet avec de la marche.
     * Permet de choisir de marcher entre deux Station géographiquement proches si aucune Ligne ne fait la jonction à l’heure voulue.
     * @param s1 Station de départ.
     * @param s2 Station d'arrivée.
     */
    public static void plan3(Station s1, Station s2) {
        //TODO: define method, return type to be discussed
    }

    /**
     * Cherche les stations proches d'une coordonnée.
     * @param coordonnee coordonnée du point de départ.
     * @param maxDistance distance maximale en km entre le point de départ et une station.
     * @param minDistance distance minimale en km entre le point de départ et une station.
     * @return la liste des stations dont la distance est majorée par maxDistance et minorée par minDistance.
     */
    static List<Station> getNearStations(Coordonnee coordonnee, double maxDistance, double minDistance){
        return Reseau.getInstance().getNearStations(coordonnee,
                                                    maxDistance < 0? Constants.DEFAULT_MAX_DISTANCE : maxDistance,
                                                    minDistance < 0? Constants.DEFAULT_MIN_DISTANCE : minDistance);
    }
}
