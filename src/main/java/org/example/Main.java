package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.People.Person;
import org.example.company.Company;
import org.example.company.Department;
import org.example.company.Worker;
import org.example.dumper.impl.JsonDumper;
import org.example.dumper.impl.XmlDumper;
import org.example.http.HTTPServer;
import org.example.shapes.Circle;
import org.example.shapes.Rectangle;
import org.example.shapes.Shape;
import org.example.shop.Cart;
import org.example.shop.Product;
import org.example.validator.impl.XmlSchemaValidator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        taskOne();
        taskTwo();
        taskThreePrep();
        taskFour();
        new HTTPServer().start();
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

    public static void taskThreePrep() {
        System.out.println("TaskThree");
        Path xmlPath = Path.of(".", "company.xml");
        Path jsonPath = Path.of(".", "company.json");

        Company company = new Company();
        company.setName("WSB");
        company.addDepartment(new Department().setName("IT")
                        .addWorker(new Worker().setName("Maks").setSurname("Grupinski").setPosition("Jester"))
                        .addWorker(new Worker().setName("Artur").setSurname("Kucinski").setPosition("Jokey")))
                .addDepartment(new Department().setName("Cleanup")
                        .addWorker(new Worker().setName("Sebuś").setSurname("Niepamietam").setPosition("Mainman")));

        JsonDumper<Company> jsonDumper = new JsonDumper<>();
        XmlDumper<Company> xmlDumper = new XmlDumper<>();
        xmlDumper.dump(xmlPath, company);
        jsonDumper.dump(jsonPath, company);
    }

    public static void taskFour() {
        System.out.println("TaskFour");
        List<Shape> shapes = new ArrayList<>();

        Path xmlPath = Path.of(".", "shapes.xml");
        Path jsonPath = Path.of(".", "shapes.json");

        JsonDumper<List<Shape>> jsonDumper = new JsonDumper<>();
        XmlDumper<List<Shape>> xmlDumper = new XmlDumper<>();

        shapes.add(new Rectangle(12, 23));
        shapes.add(new Rectangle(2, 24));
        shapes.add(new Circle(2));
        shapes.add(new Circle(14));

        jsonDumper.dump(jsonPath, shapes);
        xmlDumper.dump(xmlPath, shapes);
    }
}