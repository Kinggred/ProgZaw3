package org.example.dumper;

import org.example.validator.SchemaValidator;

import java.nio.file.Path;

public interface DataDumper<T> {
    void dump(Path path, T objToDump);
    T load(Path path, Class<T> clazz);
    T load(Path path, Class<T> clazz, SchemaValidator validator);
}
