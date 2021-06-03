package com.store.model.service;

import com.store.model.dao.DaoFactory;
import com.store.model.dao.UserDao;
import com.store.model.entity.User;
import org.apache.log4j.Logger;

import java.util.List;

public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    public User findUserByLogin(String login) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findUserByLogin(login);
        }

    }
}
