package fr.uparis.backapp.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertSame;

public class TestReseau {

    @Test
    void testGetInstance() {
        Reseau reseau1 = Reseau.getInstance();
        Reseau reseau2 = Reseau.getInstance();
        assertSame(reseau1, reseau2);
    }

    @Test
    void testGetNearStationsByOrigin() {
        //TODO Write tests
    }

    @Test
    void testGetNearByDestination() {
       //TODO write tests
    }

    @Test
    void testDjikstra() {
        //TODO write tests
    }

}
