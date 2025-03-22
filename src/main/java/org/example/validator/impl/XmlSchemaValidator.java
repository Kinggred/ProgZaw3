package org.example.validator.impl;


import org.example.validator.SchemaValidator;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.nio.file.Path;

public class XmlSchemaValidator implements SchemaValidator {
    private final Schema schema;

    public XmlSchemaValidator(Path xsdPath) {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            this.schema = factory.newSchema(new File(xsdPath.toUri()));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validate(Path xmlPath) {
        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(new File(xmlPath.toUri())));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
