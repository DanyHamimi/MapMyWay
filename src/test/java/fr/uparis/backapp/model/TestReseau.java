package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Testeur de la classe Reseau
 */
public class TestReseau {

    /**
     * Teste le Getter de l'instance de Reseau
     */
    @Test
    void testGetInstance() {
        Reseau reseau1 = Reseau.getInstance();
        Reseau reseau2 = Reseau.getInstance();
        assertSame(reseau1, reseau2);
    }

    /**
     * Teste le calcul de distance entre deux coordonnées identiques.
     */
    @Test
    void testDistanceBetween0(){
        Coordonnee origine=new Coordonnee(52.2296756, 21.0122287);
        Coordonnee destination=new Coordonnee(52.2296756, 21.0122287);
        assertEquals(0.000, Reseau.distanceBetween(origine, destination));
    }

    /**
     * Teste le calcul de distance entre deux coordonnées différentes.
     * Résultat depuis Google Maps.
     */
    @Test
    void testDistanceBetween1(){
        Coordonnee origine=new Coordonnee(48.948559, 2.063739);
        Coordonnee destination=new Coordonnee(48.94796491807184, 2.0651253924818516);
        assertEquals(0.121, Reseau.distanceBetween(origine, destination));
    }

    /**
     * Teste le calcul de distance entre deux coordonnées différentes.
     * Résultat depuis un site de calcul.
     */
    @Test
    void testDistanceBetween2(){
        Coordonnee origine=new Coordonnee(52.2296756, 21.0122287);
        Coordonnee destination=new Coordonnee(52.406374, 16.9251681);
        assertEquals(278.537, Reseau.distanceBetween(origine, destination));
    }

    /**
     * Teste si les stations proches obtenues sont celles attendues.
     */
    @Test
    void testGetNearStations(){
        //TODO Write tests
        Coordonnee coordonnee=new Coordonnee(48.948559, 2.063739);
        double maxDistance=0.5; //500 m
        double minDistance=0.1; //100 m
        Reseau.getNearStations(coordonnee, maxDistance, minDistance);
    }

    /**
     * Teste le calcul des plus courts chemins du Reseau
     */
    @Test
    void testDjikstra() {
        //TODO write tests
    }

}
