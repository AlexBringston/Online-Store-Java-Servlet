package com.store.model.dao;

import com.store.model.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User>{

    User findUserByLogin(String login);

    int countAllUsers();

    List<User> listUsersPerPage(int pageNumber, int limit);
}
