package dao;

import model.DomainObject;

public interface DAO<T extends DomainObject> 
{
    T insert(T domain);

    boolean delete(int id);

    T update(T domain);

    T read(int id);

    int clear() throws UnsupportedOperationException;

}
