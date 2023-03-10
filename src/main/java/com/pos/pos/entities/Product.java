package com.pos.pos.entities;

import jakarta.persistence.*;

@Entity
public class Product {
    private Long id;
    private String name;
    private Category category;
    private int quantity;

    private double price;
    private ProductPhoto photo;

    public void setId(Long id) { this.id = id; }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public ProductPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(ProductPhoto photo) {
        this.photo = photo;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }
}
