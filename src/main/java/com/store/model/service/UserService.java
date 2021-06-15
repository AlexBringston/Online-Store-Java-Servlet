package com.store.model.service;

import com.store.model.dao.DaoFactory;
import com.store.model.dao.UserDao;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * User service which contains methods that create dao instances and use their methods.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Method to find user by login
     * @param login user login
     * @return User if found, otherwise throws exception
     * @throws DatabaseException if user was not found
     */
    public User findUserByLogin(String login) throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findUserByLogin(login).orElseThrow(() -> new DatabaseException("Could not find user by login"));
        }
    }

    /**
     * Method to find user by id
     * @param userId user id
     * @param locale locale name
     * @return User if found, otherwise throws exception
     * @throws DatabaseException if user was not found
     */
    public User findUserById(int userId, String locale) throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findById(userId, locale).orElseThrow(() -> new DatabaseException("Could not find user"));
        }
    }

    /**
     * Method to count all users
     * @return number of registered users
     * @throws DatabaseException if error in sql happened
     */
    public int countAllUsers() throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.countAllUsers();
        }
    }

    /**
     * Method to update user
     * @param user user instance which is used to update corresponding data in table
     * @throws DatabaseException if error in sql happened
     */
    public void updateUser(User user) throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.update(user);
        }
    }

    /**
     * Method to create a user in database
     * @param user User instance which contains data to be added
     * @throws DatabaseException if error occurs
     */
    public void createUser(User user) throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.create(user);
        }
    }

    /**
     * Method to list users on given page
     * @param pageNumber number of current page
     * @param limit limit of users per page
     * @return list of users
     * @throws DatabaseException if error has occured
     */
    public List<User> listUsers(int pageNumber, int limit) throws DatabaseException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.listUsersPerPage(pageNumber, limit).orElseThrow(() -> new DatabaseException("Could not list" +
                    " users"));
        }
    }
}
