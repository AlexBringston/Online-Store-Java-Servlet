package com.store.model.dao;

import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User>{

    Optional<User> findUserByLogin(String login) throws DatabaseException;

    int countAllUsers() throws DatabaseException;

    Optional<List<User>> listUsersPerPage(int pageNumber, int limit) throws DatabaseException;
}
