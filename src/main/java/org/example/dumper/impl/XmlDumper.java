package org.example.dumper.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dumper.File;
import org.example.dumper.DataDumper;
import org.example.validator.SchemaValidator;

import java.io.IOException;
import java.nio.file.Path;

public class XmlDumper<T> implements DataDumper<T> {
    private static final XmlMapper xmlMapper = new XmlMapper();

    static {
        xmlMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
    }


    @Override
    public void dump(Path path, T objToDump) {
        try {
            String xmlContent = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objToDump);
            File.dumpTo(path, xmlContent);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write XML to file: " + path, e);
        }
    }

    @Override
    public String dump(T objToDump) {
        try {
            return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objToDump);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write XML");
        }
    }

    @Override
    public T load(Path path, Class<T> clazz) {
        try {
            String xmlContent = File.read(path);
            return xmlMapper.readValue(xmlContent, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load XML from file: " + path, e);
        }
    }

    public T load(Path path, TypeReference<T> typeReference) {
        try {
            String xmlContent = File.read(path);
            return xmlMapper.readValue(xmlContent, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load XML from file: " + path, e);
        }
    }

    @Override
    public T load(Path path, Class<T> clazz, SchemaValidator validator) {
        if (validator == null) {
            System.out.println("Validator is null, no validation done");
            return this.load(path, clazz);
        } else {
            System.out.println("Validating");
            if(!validator.validate(path)) {
                throw new RuntimeException("Validator failed to validate file: " + path);
            }
        }
        return this.load(path, clazz);
    }

    public T load(Path path, TypeReference<T> typeReference, SchemaValidator validator) {
        if (validator == null) {
            System.out.println("Validator is null, no validation done");
            return this.load(path, typeReference);
        } else {
            System.out.println("Validating");
            if(!validator.validate(path)) {
                throw new RuntimeException("Validator failed to validate file: " + path);
            }
        }
        return this.load(path, typeReference);
    }

}
