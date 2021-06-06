package com.store.model.dao;

import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;

import java.util.List;

public interface UserDao extends GenericDao<User>{

    User findUserByLogin(String login) throws DatabaseException;

    int countAllUsers() throws DatabaseException;

    List<User> listUsersPerPage(int pageNumber, int limit) throws DatabaseException;
}
