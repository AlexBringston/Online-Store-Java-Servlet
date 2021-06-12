package com.store.model.dao;

import com.store.model.exception.DatabaseException;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    void create (T entity) throws DatabaseException;
    Optional<T> findById(int id, String locale) throws DatabaseException;
    Optional<List<T>> findAll() throws DatabaseException;
    void update(T entity) throws DatabaseException;
    void delete(int id) throws DatabaseException;
    void close();
}

