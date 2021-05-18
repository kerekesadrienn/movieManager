package dao.impl;

import dao.ActorsDao;
import model.Actor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ActorsImpl implements ActorsDao {

    private EntityManager entityManager;

    public ActorsImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Visszaa egy listát, amely az összes hozzávalót tartalmazza az adatbázisban.
     * @return Egy lista, amely hozzávalókat tartalmaz.
     */
    @Override
    public List<Actor> getAllActor() {
        TypedQuery<Actor> query = entityManager.createQuery(
                "SELECT r FROM Recept r", Actor.class);
        return query.getResultList();
    }

    /**
     * Feltölti a hozzávalót az adatbázisba.
     * @param entity Az a hozzávaló, amelyet feltölt az adatbázisba.
     */
    @Override
    public void persist(Actor entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

}
