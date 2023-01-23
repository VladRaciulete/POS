package com.pos.pos.common;

import com.pos.pos.entities.Category;

public class ProductDto {
    Long id;
    String name;

    Category category;

    public ProductDto(Long id, String name, Category category) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Category getCategory() {
        return category;
    }
}
