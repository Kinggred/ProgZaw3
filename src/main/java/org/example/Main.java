package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.People.Person;
import org.example.dumper.impl.JsonDumper;
import org.example.dumper.impl.XmlDumper;
import org.example.shop.Cart;
import org.example.shop.Product;
import org.example.validator.impl.XmlSchemaValidator;

import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        taskOne();
        taskTwo();
    }

    public static void taskOne() {
        System.out.println("TaskOne");
        Cart cart = new Cart();
        cart.addProduct(new Product().setDate(Date.valueOf(LocalDate.now())).setName("First").setPrice(100));
        cart.addProduct(new Product().setDate(Date.valueOf(LocalDate.now())).setName("Second").setPrice(200));
        cart.addProduct(new Product().setDate(Date.valueOf(LocalDate.now())).setName("Third").setPrice(300));
        cart.addProduct(new Product().setDate(Date.valueOf(LocalDate.now())).setName("Fourth").setPrice(400));

        Path jsonPath = Path.of(".", "cart.json");
        Path xmlPath = Path.of(".", "cart.xml");

        JsonDumper<Cart> jsonDumper = new JsonDumper<>();
        jsonDumper.dump(jsonPath, cart);

        XmlDumper<Cart> xmlDumper = new XmlDumper<>();
        xmlDumper.dump(xmlPath, cart);

        Cart newCartXml = xmlDumper.load(xmlPath, Cart.class);
        System.out.println(newCartXml.getProducts().toString());

        Cart newCartJson = jsonDumper.load(jsonPath, Cart.class);
        System.out.println(newCartJson.getProducts().toString());
    }

    public static void taskTwo() {
        System.out.println("TaskTwo");

        Path xmlPath = Path.of(".", "people.xml");
        Path xsdPath = Path.of(".", "people.xsd");

        XmlDumper<List<Person>> xmlDumper = new XmlDumper<>();
        List<Person> newPeople = xmlDumper.load(xmlPath, new TypeReference<List<Person>>() {}, new XmlSchemaValidator(xsdPath));
        System.out.println(newPeople);
    }
}