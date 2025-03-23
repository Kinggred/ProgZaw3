package org.example.shapes;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Circle.class, name = "Circle"),
        @JsonSubTypes.Type(value = Rectangle.class, name = "Rectangle")
})
public abstract class Shape {
    String name;

    public abstract double getField();

    public abstract double getCircumference();

    public String getName() {
        return name;
    }

    public Shape(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s: %f, %f", getClass().getSimpleName(), getField(), getCircumference());
    }
}
