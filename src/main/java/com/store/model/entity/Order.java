package com.store.model.entity;

import java.sql.Timestamp;

public class Order extends Entity{

    private int userId;

    private Timestamp createdAt;

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                '}';
    }
}
