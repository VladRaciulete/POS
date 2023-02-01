package com.pos.pos.entities;


/*
Description: Aici avem Entitatea Responsabila de crearea unei tabele unde avel listate toate tranzactiile fara detalii despre ce contin ele. In cazul unor viitoare impedimente se vor unii Transactions cu TransactionsDEtails

Fields : @transaction_id: Id ul propriu zis al fiecarei tranzactii in functie de care o sa legam tabelele
         @transaction_type : tipul tranzactiei , sell/refound ;
         @payment_type : modul in care a fost achitat , CASH/CARD
         @total: suma produselor achitate

Usage :In functie de modul in care implementam logica se pot schimba detalii

 */

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
    private int scanned = 1;

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    @Id
    @GeneratedValue
    public int getTransaction_id() {
        return transaction_id;
    }

    @Basic

    public int isScanned() {
        return scanned;
    }

    public void setScanned(int scanned) {
        this.scanned = scanned;
    }

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
