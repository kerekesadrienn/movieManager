package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDAO<T,  ID extends Serializable> {
    void persist(T entity);

}
