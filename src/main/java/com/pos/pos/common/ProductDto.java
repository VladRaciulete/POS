package com.pos.pos.common;

public class ProductDto {
    Long id;
    String name;

    public ProductDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
