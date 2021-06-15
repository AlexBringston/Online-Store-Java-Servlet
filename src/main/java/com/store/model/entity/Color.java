package com.store.model.entity;

/**
 * Color entity. Is used to store data from corresponding table about certain color.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
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
