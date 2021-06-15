package com.store.model.entity;

import java.io.Serializable;

/**
 * Abstract class which contains mutual field id for all entities
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = -2103499292594760386L;
    private int id;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
