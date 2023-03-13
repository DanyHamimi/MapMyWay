package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

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
     *
     */
    @Test
    void testGetNearStationsByOrigin() {
        //TODO Write tests
    }

    /**
     *
     */
    @Test
    void testGetNearByDestination() {
       //TODO write tests
    }

    /**
     * Teste le calcul des plus courts chemins du Reseau
     */
    @Test
    void testDjikstra() {
        //TODO write tests
    }

}
