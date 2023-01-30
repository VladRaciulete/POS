package com.pos.pos.common;

public class TransactionDto {
     int transaction_id;
     String transaction_type;
     String payment_type;
     double total;

    public TransactionDto(int transaction_id, String transaction_type, String payment_type, double total) {
        this.transaction_id = transaction_id;
        this.transaction_type = transaction_type;
        this.payment_type = payment_type;
        this.total = total;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public double getTotal() {
        return total;
    }
}
