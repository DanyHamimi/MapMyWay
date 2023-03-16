package fr.uparis.backapp.Utils;

import fr.uparis.backapp.model.Coordonnee;
import fr.uparis.backapp.model.Reseau;
import fr.uparis.backapp.model.Section;
import fr.uparis.backapp.model.Station;

import java.util.List;

/**
 * Permet de calculer des itineraires en fonction de differents parametres
 */
public class Calculator {
    static double DEFAULT_MAX_DISTANCE=10.0; //distance maximale par défaut entre une coordonnée et une station, en km
    static double DEFAULT_MIN_DISTANCE=0.0; //distance minimale par défaut à marcher pendant le trajet, en km

    /**
     * Calcul un itineraire depuis une Coordonnee a une autre
     * @param origin Coordonnee d'origine
     * @param destination Coordonnee de destination
     */
    public static void iteneraire(Coordonnee origin, Coordonnee destination) {
        //TODO define method
    }

    /**
     * Trajet dans Reseau.
     * Renvoie une sequence de transport, avec horaire, pour se rendre d'une station a une autre.
     * @param s1 Station de depart
     * @param s2 Station d'arrivee
     * @return une sequence de transport, avec horaire, pour se rendre de s1 a s2
     */
    public static List<Section> plan0(Station s1, Station s2) {
        //TODO define method
        return null;
    }

    /**
     * Trajet optimise dans le Reseau.
     * Renvoie une sequence optimale en termes de temps comme de distance parcourue.
     * @param s1 Station de depart
     * @param s2 Station d'arrivee
     * @return une sequence optimale en termes de temps comme de distance parcourue
     */
    public static List<Section> plan1(Station s1, Station s2) {
        //TODO define method
        return null;
    }


    /**
     * Trajet avec debut ou fin a pied.
     * Permet de partir et d'arriver a n'importe quelle Coordonnee geographique (pas seulement des arrets du reseau).
     * @param s1 Station de depart
     * @param s2 Station d'arrivee
     */
    public static void plan2(Station s1, Station s2) {
        //TODO define method, return type to be discussed
        // Remplacer Station par Coordonnee ?
    }

    /**
     * Trajet avec de la marche.
     * Permet de choisir de marcher entre deux Station géographiquement proches si aucune Ligne ne fait la jonction à l’heure voulue.
     * @param s1 Station de depart
     * @param s2 Station d'arrivee
     */
    public static void plan3(Station s1, Station s2) {
        //TODO define method, return type to be discussed
    }

    /**
     * Cherche les stations proches d'une coordonnée.
     * @param coordonnee coordonnée du point de départ
     * @param maxDistance distance maximale en km entre le point de départ et une station
     * @param minDistance distance minimale en km entre le point de départ et une station
     * @return la liste des stations dont la distance est majorée par maxDistance et minorée par minDistance
     */
    private static Station[] getNearStations(Coordonnee coordonnee, double maxDistance, double minDistance){
        return Reseau.getNearStations(coordonnee, maxDistance, minDistance);
    }
}