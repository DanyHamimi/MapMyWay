package fr.uparis.backapp.Utils;

import fr.uparis.backapp.model.Cordonnee;
import fr.uparis.backapp.model.Section;
import fr.uparis.backapp.model.Station;

import java.util.List;

/**
 * Permet de calculer des itineraires en fonction de differents parametres
 */
public class Calculator {

    /**
     * Calcul un itineraire depuis une Coordonnee a une autre
     * @param origin Coordonnee d'origine
     * @param destination Coordonnee de destination
     */
    public static void iteneraire(Cordonnee origin, Cordonnee destination) {
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
     * Permet de partir de, et d'arriver a, n'importe quelle Coordonnee geographique (pas seulement des arrets du reseau).
     * @param s1 Station de depart
     * @param s2 Station d'arrivee
     */
    public static void plan2(Station s1, Station s2) {
        //TODO define method, return type to be discussed
        // Remplacer Station par Coordonnee ?
    }

    /**
     * Trajet avec a pied.
     * Permet de choisir de marcher entre deux Station géographiquement proches si aucune Ligne ne fait la jonction à l’heure voulue.
     * @param s1 Station de depart
     * @param s2 Station d'arrivee
     */
    public static void plan3(Station s1, Station s2) {
        //TODO define method, return type to be discussed
    }
}
