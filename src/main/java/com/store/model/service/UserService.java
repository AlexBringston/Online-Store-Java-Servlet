package com.store.model.service;

import com.store.model.dao.DaoFactory;
import com.store.model.dao.UserDao;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import org.apache.log4j.Logger;

import java.util.List;

public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    public User findUserByLogin(String login) throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findUserByLogin(login);
        }
    }

    public User findUserById(int userId, String locale) throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findById(userId, locale);
        }
    }

    public int countAllUsers() throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.countAllUsers();
        }
    }

    public void updateUser(User user) throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.update(user);
        }
    }
    public List<User> listUsers(int pageNumber, int limit) throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.listUsersPerPage(pageNumber, limit);
        }
    }
}
