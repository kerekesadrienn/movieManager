package utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBManager implements AutoCloseable{

    protected static final Logger log = LoggerFactory.getLogger(DBManager.class);

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManager entityManager;

    static{
        entityManagerFactory = Persistence.createEntityManagerFactory("movieDB");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static EntityManager getInstance() {
        return entityManager;
    }

    @Override
    public void close() throws Exception {
        if (entityManagerFactory != null){
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
