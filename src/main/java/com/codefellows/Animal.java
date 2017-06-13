package com.codefellows;

/**
 * Created by Brad on 6/12/2017.
 */
public abstract class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    abstract void speak();
}
