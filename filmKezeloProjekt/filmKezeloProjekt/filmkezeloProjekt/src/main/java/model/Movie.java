package model;

import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity(name = "Movie")
public class Movie {

    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(Movie.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })

    private List<Actor> actors = new ArrayList<>();

    public Movie() {
    }

    /**
     */
    public Movie(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
        log.info("Create a " + name +" recipe.");
    }

    public Movie(String name, String description, String category, List<Actor> actors) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.actors = actors;
    }

    /**
     */
    public void addActors(Actor actor){
        this.actors.add(actor);
        actor.getMovies().add(this);
        log.info("Add a(n) "+ actor.getName() + " actor to " + this.name + " movies");
    }

    /**
     */
    public long getId() {
        return id;
    }

    /**
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     */

    public String getName() {
        return name;
    }

    /**
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     */
    public String getDescription() {
        return description;
    }

    /**
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     */
    public String getCategory() {
        return category;
    }

    /**
     */
    public void setCategory(String movieType) {
        this.category = movieType;
    }

    /**
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     */
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    /**
     */
    @Override
    public String toString() {
        return "Film: " +
                "  cím: " + name +
                "  leírás: " + description +
                "  film típusa: " + category +
                "  szereplők: " + actors;
    }

    /**
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(name, movie.name) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(category, movie.category) &&
                Objects.equals(actors, movie.actors);
    }

}
