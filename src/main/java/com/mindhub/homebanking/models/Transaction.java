package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    public long id;
    private double amount;
    private String description;

    private LocalDateTime date;

    private TransactionType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")

    private Account account;
    public Transaction(){}

    public Transaction (double amount, String description, LocalDateTime date, TransactionType type, Account account){

        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.account = account;
    }

    public long getId(){ return id; }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
