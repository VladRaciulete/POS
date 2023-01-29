package com.pos.pos.entities;
import jakarta.persistence.*;

/*
Description: Aici avem Entitatea Responsabila de crearea unei tabele cu detaliile fiecarei tranzactii.

Fields : @transaction_id: Id ul propriu zis al fiecarei tranzactii in functie de care o sa legam tabelele
         @product_id : id-ul fiecarui produs asociat tranzactiei
         @quantity: cantitea aferenta fiecarui produs
         @price : pretul cu care a fsot achizitioinat/deturnat , util pt calculul totalului

Usage : se foloseste in paralel cu tabela Transactions unde sunt listate tranzactiile fara a intra in detalii

 */

@Entity
public class TransactionDetails {
    private Long transaction_id;
    private double price;
    private Long product_id;
    private int quantity;


    public TransactionDetails() {
    }

    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }

    @Id
    @GeneratedValue
    public Long getTransaction_id() {
        return transaction_id;
    }


    @Basic
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
