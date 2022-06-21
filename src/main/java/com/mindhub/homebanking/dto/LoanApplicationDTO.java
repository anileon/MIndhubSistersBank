package com.mindhub.homebanking.dto;



public class LoanApplicationDTO {

    private long loanId;

    private double amount;

    private int payment;

    private String accountDestiny;

   public LoanApplicationDTO (){}

    public LoanApplicationDTO(int loanId,double amount,int payment,String accountDestiny){
       this.loanId= loanId;
       this.amount= amount;
       this.payment= payment;
       this.accountDestiny= accountDestiny;
    }

    public long getLoanId() {return loanId;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}
    public int getPayment() {return payment;}

    public void setPayment(int payment) {this.payment = payment;}

    public String getAccountDestiny() {return accountDestiny;}

    public void setAccountDestiny(String accountDestiny) {this.accountDestiny = accountDestiny;}
}
