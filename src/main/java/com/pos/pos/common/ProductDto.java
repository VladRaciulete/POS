package com.pos.pos.common;

import com.pos.pos.entities.Category;

public class ProductDto {
    //Obiect de transfer pentru produs
    Long id;
    String name;
    int quantity;
    double price;
    Category category;


    public ProductDto(Long id, String name, int quantity, double price, Category category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() { return price; }
    public Category getCategory() {
        return category;
    }
}
