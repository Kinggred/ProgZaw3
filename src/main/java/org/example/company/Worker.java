package org.example.company;

public class Worker {
    public String getName() {
        return name;
    }

    public Worker setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Worker setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public Worker setPosition(String position) {
        this.position = position;
        return this;
    }

    private String name;
    private String surname;
    private String position;
}
