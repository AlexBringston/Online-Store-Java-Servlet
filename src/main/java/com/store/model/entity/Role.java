package com.store.model.entity;

/**
 * Enum which stores all possible user roles on site and methods to get them.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public enum Role {
    ADMIN, CLIENT, GUEST;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}