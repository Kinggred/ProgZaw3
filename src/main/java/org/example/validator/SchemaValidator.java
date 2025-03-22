package org.example.validator;

import java.nio.file.Path;

public interface SchemaValidator {
    boolean validate(Path path);
}
