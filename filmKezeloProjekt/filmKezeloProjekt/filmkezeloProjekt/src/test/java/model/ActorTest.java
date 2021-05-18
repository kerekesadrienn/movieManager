package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActorTest {


    private Actor actor;

    @Before
    public void setUp() throws Exception {
        actor = new Actor();

    }

    @Test
    public void gettersTest(){

        List<Movie> movieTest = new ArrayList<>();
        movieTest.add(new Movie("Csillagok között", "Nagyon szép film.", Category.SCIFI.name()));
        movieTest.add(new Movie("Hivatali Patkányok", "A kedvenc vígjátékom.", Category.VÍGJÁTÉK.name()));

        actor.setId(Long.valueOf(1));
        assertEquals(1, actor.getId());
        actor.setName("Angelina Jolie");
        assertEquals("Angelina Jolie", actor.getName());
        actor.setMovies(movieTest);
        assertEquals(movieTest, actor.getMovies());
    }
}