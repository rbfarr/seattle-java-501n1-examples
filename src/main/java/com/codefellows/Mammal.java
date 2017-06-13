package com.codefellows;

/**
 * Created by Brad on 6/12/2017.
 */
public class Mammal extends Animal {
    public Mammal(String name) {
        super(name);
    }

    public void speak() {
        System.out.println("Mammal");
    }
}
