package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.ClientLoan;


public class ClientLoanDTO {
    private long id;

    private long idLoan;
    private String name ;
    private double amount;
    private int payment;


    public ClientLoanDTO(){}

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.idLoan = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payment = clientLoan.getPayments();
    }

    public long getId() {return id;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}

    public int getPayment() {return payment;}

    public void setPayment(int payment) {this.payment = payment;}

    public String getName() {return name;}


}
