package dao;

import model.Movie;

import java.util.List;

public interface MovieDAO extends GenericDAO<Movie,Long> {
    List<Movie> getAllMovie();
    List<Movie> searchMovie(List<String> ingredientsList);
    List<Movie> searchFilteredMovie(List<String> mealTypeList, List<String> ingredientsList);
    List<Movie> searchContainedMovie(List<String> ingredientsTypeList, List<String> mealTypeList, List<String> ingredientsList);
    List<Movie> searchMoviesByCategories(List<String> categories);
}
