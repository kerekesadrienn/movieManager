package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MovieTest {

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        movie = new Movie();
    }


    @Test
    public void gettersTest(){
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Angelina Jolie"));
        actors.add(new Actor("Brad Pitt"));

        movie.setId(Long.valueOf(1));
        assertEquals(1, movie.getId());
        movie.setName("Csillagok között");
        assertEquals("Csillagok között", movie.getName());
        movie.setDescription("Nagyon szép film.");
        assertEquals("Nagyon szép film.", movie.getDescription());
        movie.setCategory(Category.VÍGJÁTÉK.name());
        assertEquals(Category.VÍGJÁTÉK.name(), movie.getCategory());
        movie.setActors(actors);
        assertEquals(actors, movie.getActors());
    }
}