package org.example.shapes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rectangle extends Shape implements Resizable {
        @JsonProperty
        double width;
        @JsonProperty
        double height;

        @JsonCreator
        public Rectangle(@JsonProperty("width") double width, @JsonProperty("height") double height) {
            super("Rectangle");
            this.width = width;
            this.height = height;
        }

        @Override
        public double getField() {
            return width * height;
        }

        @Override
        public double getCircumference() {
            return width + height * 2;
        }

        @Override
        public void resize(double factor) {
            this.width = factor * width;
            this.height = factor * height;
        }
}

