package com.store.model.entity;

/**
 * Category entity. Is used to store data from corresponding table about certain category.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class Category extends Entity{
    private static final long serialVersionUID = -1279651331877848569L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
