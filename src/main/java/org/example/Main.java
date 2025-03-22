package org.example;

import org.example.dumper.impl.JsonDumper;
import org.example.dumper.impl.XmlDumper;
import org.example.shop.Cart;
import org.example.shop.Product;

import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        taskOne();
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
}