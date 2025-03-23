package org.example.dumper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dumper.File;
import org.example.dumper.DataDumper;
import org.example.validator.SchemaValidator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class JsonDumper<T> implements DataDumper<T> {
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void dump(Path path, T objToDump) {
        Optional<String> mappedObj;
        try {
            mappedObj = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objToDump).describeConstable();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }

        mappedObj.ifPresent(s -> File.dumpTo(path, s));
    }

    @Override
    public String dump(T objToDump) {
        Optional<String> mappedObj;
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objToDump);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    public T load(String objToLoad, Class<T> clazz) {
        try {
            return objectMapper.readValue(objToLoad, clazz);
        } catch (JsonProcessingException e) {
            System.out.println("Failed to parse as JSON " + objToLoad);
            throw new RuntimeException("Failed to parse as JSON " + objToLoad, e);
        }
    }

    @Override
    public T load(Path path, Class<T> clazz) {
        try {
            String jsonContent = File.read(path);
            return objectMapper.readValue(jsonContent, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON from file: " + path, e);
        }
    }

    @Override
    public T load(Path path, Class<T> clazz, SchemaValidator validator) {
        System.out.println("No validation for JSON");
        return this.load(path, clazz);
    }

    public T load(Path path, TypeReference<T> typeReference) {
        try {
            String jsonContent = File.read(path);
            return objectMapper.readValue(jsonContent, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load XML from file: " + path, e);
        }
    }
}
