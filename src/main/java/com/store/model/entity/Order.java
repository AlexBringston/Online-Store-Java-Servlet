package com.store.model.entity;

import java.sql.Timestamp;

public class Order extends Entity{

    private int userId;

    private User user;

    private Timestamp createdAt;

    private String status;

    public Order() {
    }

    public Order(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                '}';
    }
}
