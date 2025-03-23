package org.example.shapes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Circle extends Shape {
    @JsonProperty
    private final double radius;

    @JsonCreator
    public Circle(@JsonProperty("width") double radius) {
        super("Circle");
        this.radius = radius;
    }

    @Override
    public double getField() {
        return radius*radius*3.14;
    }

    @Override
    public double getCircumference() {
        return radius * 3.14 * 2;
    }
}

