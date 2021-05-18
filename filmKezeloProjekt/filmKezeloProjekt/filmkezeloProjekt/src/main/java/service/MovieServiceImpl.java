package service;

import dao.MovieDAO;
import model.Actor;
import model.Movie;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    private MovieDAO dao;

    public MovieServiceImpl(MovieDAO dao) {
        this.dao = dao;
    }

    /**
     */
    @Override
    public void createActorsAddToMovie(List<Actor> actorList, Movie movie) {
        for (int i = 0; i < actorList.size(); i++) {
            movie.addActors(actorList.get(i));
        }
    }

    /**
     */
    @Override
    public void createMovie(Movie recipe) {
        dao.persist(recipe);
    }

    /**
     */
    @Override
    public List<Movie> searchMovie(List<String> actors) {
        return dao.searchMovie(actors);
    }

    /**
     */
    @Override
    public List<Movie> searchFilteredMovie(List<String> movieTypeList, List<String> actorsList) {
        return dao.searchFilteredMovie(movieTypeList, actorsList);
    }

    @Override
    public List<Movie> searchContainedMovie(List<String> actorsTypeList, List<String> movieTypeList, List<String> actorsList) {
        return dao.searchContainedMovie(actorsTypeList, movieTypeList, actorsList);
    }

    @Override
    public List<Movie> getAllMovie() {
        return dao.getAllMovie();
    }

    @Override
    public List<Movie> getMoviesByCategories(List<String> categories) {
        return dao.searchMoviesByCategories(categories);
    }
}
