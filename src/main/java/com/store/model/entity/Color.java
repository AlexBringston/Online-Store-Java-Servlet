package com.store.model.entity;

public class Color extends Entity{

    private static final long serialVersionUID = 5042909005151891887L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Color{" +
                "name='" + name + '\'' +
                '}';
    }
}
