package org.example.dumper.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dumper.File;
import org.example.dumper.DataDumper;

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
    public T load(Path path, Class<T> clazz) {
        try {
            String xmlContent = File.read(path);
            return xmlMapper.readValue(xmlContent, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load XML from file: " + path, e);
        }
    }

    @Override
    public T load(Path path, Class<T> clazz, String validator) {
//            if (!validator.validate(xmlContent)) {
//                throw new RuntimeException("XML validation failed for file: " + filePath);
//            }
        return this.load(path, clazz);
    }
}
