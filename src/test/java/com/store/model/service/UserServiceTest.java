package com.store.model.service;

import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void findUserByLogin() throws DatabaseException {
        User user = new UserService().findUserByLogin("Admin");
        assertEquals("Oleksii", user.getFirstName());
    }

    @Test
    void findUserById() throws DatabaseException {
        User user = new UserService().findUserById(2,"");
        assertEquals(2,user.getId());
    }

    @Test
    void countAllUsers() throws DatabaseException {
        assertEquals(2,new UserService().countAllUsers());
    }

    @Test
    void listUsers() throws DatabaseException {
        assertFalse(new UserService().listUsers(1,8).isEmpty());
    }
}