package service;

import dao.MovieDAO;
import dao.impl.MovieImpl;
import model.Actor;
import model.Category;
import utility.DBManager;
import model.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class MovieServiceImplTest {


    private List<String> searchItem;
    private List<Actor> actors;
    private List<String> mealType;
    private List<Movie> solutionMovie;

    private MovieDAO dao= new MovieImpl(DBManager.getInstance());
    private MovieService movieService = new MovieServiceImpl(dao);


    private Movie createTestMovie() {

        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Angelina Jolie"));
        actors.add(new Actor("Brad Pitt"));
        Movie movie = new Movie("Mr. and Mrs. Smith", "Izgalmas film.", Category.VÍGJÁTÉK.name());
        movieService.createActorsAddToMovie(actors, movie);
        movieService.createMovie(movie);

        return movie;
    }

    private Movie movie = createTestMovie();

    @Test
    public void searchMovie() {

        searchItem = new ArrayList<>();
        searchItem.add("Angelina Jolie");
        searchItem.add("Brad Pitt");
        solutionMovie = movieService.searchMovie(searchItem);

       /* if (solutionMovie.isEmpty())
            assertEquals(movie.getId(), solutionMovie.get(0).getId());
        else {
            for (int i = 0; i < solutionMovie.size(); i++) {
                if (movie.getId() == solutionMovie.get(i).getId()) {
                    assertEquals(movie.getId(), solutionMovie.get(i).getId());
                    assertEquals(movie.getName(), solutionMovie.get(i).getName());
                    assertEquals(movie.getDescription(), solutionMovie.get(i).getDescription());
                    assertEquals(movie.getCategory(), solutionMovie.get(i).getCategory());
                }
            }
        }*/
    }

}