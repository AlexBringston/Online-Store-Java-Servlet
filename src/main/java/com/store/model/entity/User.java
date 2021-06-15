package com.store.model.entity;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * User entity. Is used to store data from corresponding table about certain user. Also it implements
 * HttpSessionBindingListener to check when user is trying to log in from new place and thus log out previous session.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class User extends Entity implements HttpSessionBindingListener {
    private static final Map<User, HttpSession> logins = new HashMap<>();

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private Timestamp createdAt;

    private int roleId;

    private Role role;

    private String status;

    private BigDecimal balance;

    public User() {
    }

    public User(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", roleId=" + roleId +
                ", role=" + role +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        HttpSession session = logins.remove(this);
        if (session != null) {
            session.invalidate();
        }
        logins.put(this, event.getSession());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        logins.remove(this);
    }

    @Override
    public int hashCode() {
        return (getId() != 0) ? (this.getClass().hashCode() + getId()) : super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof User) && (getId() != 0)) {
            return getId() == ((User) obj).getId();
        } else {
            return obj == this;
        }
    }
}
