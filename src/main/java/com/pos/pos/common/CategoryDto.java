package com.pos.pos.common;

public class CategoryDto {
    //Obiect de transfer pentru categorie

    private Long id;
    private String name;

    public CategoryDto(Long id, String name) {
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
