package com.pos.pos.common;

import com.pos.pos.entities.Product;

import java.util.List;

public class ProductsByCategoryDto {

    private Long categoryId;

    private String categoryName;

    private List<ProductDto> products;

    public ProductsByCategoryDto(Long categoryId, String categoryName, List<ProductDto> products) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.products = products;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}
