package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.Provider;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void createNeighbourWithSuccess(){
        Neighbour neighbourToCreate = new Neighbour(13,"Marc","https://i.pravatar.cc/150?u=a042581f4e29026703d","Saint-Pierre-du-Mont ; 5km","+33 6 86 57 90 14","Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot.");
        service.createNeighbour(neighbourToCreate);
        assertTrue(service.getNeighbours().contains(neighbourToCreate));

    }

    @Test
    public void addFavoriteNeighbourWithSuccess(){
        Neighbour favoriteNeighbour = service.getNeighbours().get(0);
        service.addFavoriteNeighbour(favoriteNeighbour);
        assertFalse(service.getFavoriteNeighbours().isEmpty());

    }

    @Test
    public void deleteFavoriteNeighbourWithSuccess() {
        Neighbour favoriteNeighbourToDelete = service.getNeighbours().get(0);
        service.addFavoriteNeighbour(favoriteNeighbourToDelete);
        assertTrue(service.getFavoriteNeighbours().contains(favoriteNeighbourToDelete));
        service.deleteFavoriteNeighbour(favoriteNeighbourToDelete);
        assertFalse(service.getFavoriteNeighbours().contains(favoriteNeighbourToDelete));
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
        Neighbour favneighbour1 = service.getNeighbours().get(0);
        Neighbour favneighbour2 = service.getNeighbours().get(1);
        service.addFavoriteNeighbour(favneighbour1);
        service.addFavoriteNeighbour(favneighbour2);
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        assertTrue(service.getFavoriteNeighbours().contains(favneighbour1));
        assertTrue(service.getFavoriteNeighbours().contains(favneighbour2)); }




}
