package org.example.model.daos;


import org.example.model.exceptions.DAOException;

import java.util.List;

/**
 * Interfície que conté els mètodes que s'utilitzen a la capa DAO
 * @param <T>
 */
public interface DAO <T>{

    T get(String id) throws DAOException;

    List<T> getAll() throws DAOException;
    void crearTabla() throws DAOException;
    //CRUD
    void update(T obj,String nomAntic) throws DAOException;

    void delete(T obj) throws DAOException;

    void insert(T obj) throws DAOException;

    void save() throws DAOException;
}
