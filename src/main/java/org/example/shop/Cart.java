package org.example.shop;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public Cart addProduct(Product products) {
        this.products.add(products);
        return this;
    }
}
