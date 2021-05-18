package dao.impl;

import dao.MovieDAO;
import model.Actor;
import model.Movie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

public class MovieImpl implements MovieDAO {

    private EntityManager entityManager;
    private List<Movie> filteredMovieList;

    public MovieImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Visszaad egy listát, mely az adatbázisban szereplő összes filmet tartalmazza.
     * @return Egy lista, mely az összes filmet tartalmazza.
     */
    @Override
    public List<Movie> getAllMovie() {
        TypedQuery<Movie> query = entityManager.createQuery(
                "SELECT r FROM Movie r", Movie.class);
        return query.getResultList();
    }

    /**
     * Feltölti az adatbázisba a filmeket.
     * @param entity Az a film, amelyet feltöltünk az adatbázisba.
     */
    @Override
    public void persist(Movie entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

    }

    /**
     * Visszaadja azoknak a filmeknek a listáját,
     * amelyekben a megadott színészek szerepelnek.
     * @param actorList Az a hozzávalólista, amelyet meg szeretnénk keresni a receptek hozzávalói közül.
     * @return Recept lista.
     */
    @Override
    public List<Movie> searchMovie(List<String> actorList) {

        List<Movie> solution = new ArrayList<>();

        TypedQuery<Movie> query1 = entityManager.createQuery("SELECT r FROM Movie r", Movie.class);

        for (int i = 0; i < query1.getResultList().size(); i++) {
            if (query1.getResultList().get(i).getActors().stream().map(Actor::getName).allMatch(actorList::contains))
                solution.add(query1.getResultList().get(i));

            for(int j = 0; j < actorList.size(); j++){
                if(query1.getResultList().get(i).getName().equals(actorList.get(j))){
                    solution.add(query1.getResultList().get(i));
                }
            }
        }
        filteredMovieList = solution;

        return solution;
    }


    /**
     * Visszaadja azoknak a filmeknek a listáját, mely a kereső függvény eredményeit tartalmazva
     * szűri még a listát a színészek megadásával.
     * @param mealTypeList Olyan lista, mellyel szűrni szeretnénk az eredményül kapott receptünk listáját.
     * @param actorList Megadjuk a hozzávalólistát, amelyre a keresés feltétele teljesült, hogy az eredményben végezzük el a szűrést.
     * @return Recept lista.
     */
    @Override
    public List<Movie> searchFilteredMovie(List<String> mealTypeList, List<String> actorList) {
        List<Movie> solution = searchMovie(actorList);
        filteredMovieList = new ArrayList<>();
        for (Movie aSolution : solution) {
            if (mealTypeList.stream().anyMatch(aSolution.getCategory()::contains)) {
                filteredMovieList.add(aSolution);
            }
        }
        return filteredMovieList;
    }

    /**
     * Visszaadja azoknak a recepteknek a listáját, mely a kereső függvény eredményeit tartalmazva
     * szűri még a listát a hozzávaló típusának megadásával, amelyet a recept mindenképp tartalmazzon.
     * @param ingredientsTypeList Olyan lista, mellyel szűrni szeretnénk az eredményül kapott receptünk listáját.
     * @param mealTypeList Ha szűrtünk ételtípusra, akkor az erre leszűrt receptlistában fogja elvégezni a további szűrést.
     * @param ingredientsList Ha ételtípusra nem szűrtünk, akkor a szűrést a hozzávaló keresés eredménye alapján fogja elvégezni.
     * @return Recept lista.
     */
    @Override
    public List<Movie> searchContainedMovie(List<String> ingredientsTypeList, List<String> mealTypeList, List<String> ingredientsList) {

        List<Movie> containedRecipeList = new ArrayList<>();
        List<String> typeList = new ArrayList<>();

        if (!mealTypeList.isEmpty())
            filteredMovieList = searchFilteredMovie(mealTypeList, ingredientsList);
        else
            filteredMovieList = searchMovie(ingredientsList);


        for (int i = 0; i < filteredMovieList.size(); i++) {
            for (int j = 0; j < filteredMovieList.get(i).getActors().size(); j++){
                typeList.add(filteredMovieList.get(i).getActors().get(j).getType());
            }

            if (ingredientsTypeList.stream().allMatch(typeList::contains)){
                containedRecipeList.add(filteredMovieList.get(i));
            }
            typeList = new ArrayList<>();
        }
        return containedRecipeList;
    }

    @Override
    public List<Movie> searchMoviesByCategories(List<String> categories) {
        List<Movie> solution = new ArrayList<>();

        TypedQuery<Movie> query1 = entityManager.createQuery("SELECT r FROM Movie r", Movie.class);

        for (int i = 0; i < query1.getResultList().size(); i++) {
            for(int j = 0; j < categories.size(); j++){
                if (query1.getResultList().get(i).getCategory().equals(categories.get(j)))
                    solution.add(query1.getResultList().get(i));
            }

        }
        return solution;

    }
}