package com.codefellows;

/**
 * Created by Brad on 6/12/2017.
 */
public class Person {
    private int age; //optional
    private String name; //required
    private String homeTown; //optional

    private Person(Builder builder) {
        this.age = builder.age;
        this.name = builder.name;
        this.homeTown = builder.homeTown;
    }

    public static class Builder {
        private int age;
        private String name;
        private String homeTown;

        public Builder(String name) {
            this.name = name;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder homeTown(String homeTown) {
            this.homeTown = homeTown;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
