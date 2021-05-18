package service;

import model.Actor;
import model.Movie;

import java.util.List;

public interface MovieService {
    void createActorsAddToMovie(List<Actor> ingredientsList, Movie recipe);
    void createMovie(Movie recipe);
    List<Movie> searchMovie(List <String> actors);
    List<Movie> searchFilteredMovie(List <String> category, List<String> actors);
    List<Movie> searchContainedMovie(List <String> ingredientsTypeList, List <String> mealTypeList, List<String> ingredientsList);
    List<Movie> getAllMovie();
    List<Movie> getMoviesByCategories(List<String> categories);
}
