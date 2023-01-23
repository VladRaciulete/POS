package com.pos.pos.common;

import com.pos.pos.entities.Category;

public class ProductDto {
    Long id;
    String name;
    int quantity;
    Category category;


    public ProductDto(Long id, String name, int quantity, Category category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
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
    public Category getCategory() {
        return category;
    }
}
