package com.pos.pos.common;

public class TransactionDetailsDto {
    private int transaction_id;
    private double price;
    private Long product_id;
    private String product_name;
    private int quantity;
    private int transactionDetails_id;

    public TransactionDetailsDto() {
    }

    public TransactionDetailsDto(int transaction_id, double price, Long product_id, String product_name, int quantity, int transactionDetails_id) {
        this.transaction_id = transaction_id;
        this.price = price;
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.transactionDetails_id = transactionDetails_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

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

    public int getTransactionDetails_id() {
        return transactionDetails_id;
    }

    public void setTransactionDetails_id(int transactionDetails_id) {
        this.transactionDetails_id = transactionDetails_id;
    }
}
