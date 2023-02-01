package com.pos.pos.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TransactionDetails {
    private int transactionDetails_id;
    private int transaction_id;
    private double price;
    private Long product_id;
    private int quantity;

    public void setTransactionDetails_id(int id) {
        this.transactionDetails_id = id;
    }

    @Id
    @GeneratedValue
    public int getTransactionDetails_id() {
        return transactionDetails_id;
    }

    @Basic
    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
