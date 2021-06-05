package com.store.model.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product extends Entity{
    private static final long serialVersionUID = 1035127111646328882L;

    private String name;
    private String nameUK;

    private String imageLink;

    private BigDecimal price;

    private String category;

    private String color;

    private String size;

    private Timestamp createdAt;

    public Product() {
    }

    public Product(int id, String name,String nameUK, String imageLink, BigDecimal price, String category,
                   String size, String color) {
        super(id);
        this.name = name;
        this.nameUK = nameUK;
        this.imageLink = imageLink;
        this.price = price;
        this.category = category;
        this.size = size;
        this.color = color;
    }

    public Product(String name, String nameUK, String imageLink, BigDecimal price, String category,
                   String size, String color) {
        this.name = name;
        this.nameUK = nameUK;
        this.imageLink = imageLink;
        this.price = price;
        this.category = category;
        this.size = size;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameUK() {
        return nameUK;
    }

    public void setNameUK(String nameUK) {
        this.nameUK = nameUK;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
