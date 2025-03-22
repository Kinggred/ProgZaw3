package org.example.People;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Person {
    @JacksonXmlProperty(localName = "name")
    private String name;
    @JacksonXmlProperty(localName = "surname")
    private String surname;
    @JacksonXmlProperty(localName = "age")
    private int age;

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Person setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }
}
