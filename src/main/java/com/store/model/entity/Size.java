package com.store.model.entity;

/**
 * Size entity. Is used to store data from corresponding table about certain size.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
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
