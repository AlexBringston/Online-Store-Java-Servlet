package com.store.model.entity;

public class Size extends Entity{
    private static final long serialVersionUID = 2852383536875534610L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Size{" +
                "name='" + name + '\'' +
                '}';
    }
}
