package model;

import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Actor")
public class Actor {

    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(Actor.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();

    private String type;

    public Actor() {
    }

    public Actor(String name, String type) {
        this.name = name;
        this.type = type;
        log.info("Create a(n) " + this.name + " actor");
    }
    public Actor(String name) {
        this.name = name;
        log.info("Create a(n) " + this.name + " actor");
    }


    /**
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    /**
     */
    public long getId() {
        return id;
    }

    /**
     */
    public void setId(long id) {
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
    public String getType() {
        return type;
    }

    /**
     */

    public void setType(String type) {
        this.type = type;
    }

    public void addMovie(Movie movie){
        this.movies.add(movie);
        movie.getActors().add(this);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name) &&
                Objects.equals(type, actor.type);
    }

}
