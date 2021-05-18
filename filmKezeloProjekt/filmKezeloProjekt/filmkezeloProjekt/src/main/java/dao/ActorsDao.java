package dao;

import model.Actor;

import java.util.List;

public interface ActorsDao extends GenericDAO<Actor, Long> {
    List<Actor> getAllActor();
}
