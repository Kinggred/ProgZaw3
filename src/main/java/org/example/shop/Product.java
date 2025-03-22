package org.example.shop;


import java.util.Date;

public class Product {
    private String name;
    private int price;
    private Date date;

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Product setPrice(int price) {
        this.price = price;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Product setDate(Date date) {
        this.date = date;
        return this;
    }
}
