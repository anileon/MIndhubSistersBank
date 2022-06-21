package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;


public class TransactionDTO {

        private long id;
        private double amount;
        private String description;

        private LocalDateTime date;

        private TransactionType type;


        public TransactionDTO(){}

        public TransactionDTO(Transaction transaction){
            this.id = transaction.getId();
            this.amount = transaction.getAmount();
            this.description = transaction.getDescription();
            this.date = transaction.getDate();
            this.type = transaction.getType();
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

        public TransactionType getType() {
            return type;
        }

        public void setType(TransactionType type) {
            this.type = type;
        }
    }


