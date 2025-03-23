package org.example.dumper;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.validator.SchemaValidator;

import java.io.IOException;
import java.nio.file.Path;

public interface DataDumper<T> {
    void dump(Path path, T objToDump);
    String dump(T objToDump);
    T load(Path path, Class<T> clazz);
    T load(Path path, Class<T> clazz, SchemaValidator validator);
    T load(Path path, TypeReference<T> typeReference);


}
