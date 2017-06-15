package com.codefellows;

/**
 * Created by Brad on 6/12/2017.
 */
public class Person implements Comparable<Person> {
    private int age; //optional
    private String name; //required
    private String homeTown; //optional

    private Person(Builder builder) {
        this.age = builder.age;
        this.name = builder.name;
        this.homeTown = builder.homeTown;
    }

    @Override
    public int compareTo(Person p) {
        return name.compareTo(p.getName());
    }

    public String getName() {
        return name;
    }

    public void celebrateBirthday() {
        age++;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("Hi. My name is %s. I am %d years old.", name, age);
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
