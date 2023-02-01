package com.pos.pos.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Transaction {

    private int transaction_id;

    private String transaction_type;
    private String payment_type;
    private double total;

    public void setTransaction_id(int id) {
        this.transaction_id = id;
    }

    @Id
    @GeneratedValue
    public int getTransaction_id() {
        return transaction_id;
    }


    @Basic
    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
