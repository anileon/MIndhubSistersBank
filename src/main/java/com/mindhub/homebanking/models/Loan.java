package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private int maxAmount;

    @ElementCollection
    @Column(name = "payments")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy ="loan",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan(){}
    public Loan (String name, double maxAmount, List<Integer> payments){
        this.name = name;
        this.maxAmount = (int) maxAmount;
        this.payments = payments;
    }

    public long getId() {
        return id;
    }

    public String getName() {return name;} public void setName(String name) {
        this.name = name;
    }

    public int getMaxAmount() {return maxAmount;}

    public void setMaxAmount(double maxAmount) {this.maxAmount = (int) maxAmount;}

    public List<Integer> getPayments() {return payments;}

    public void setPayments(List<Integer> payments) {this.payments = payments;}

    public void addClientLoan (ClientLoan clientLoan){
        clientLoan.setLoan (this);
        clientLoans.add (clientLoan);
    }

    public List<Client>getClients(){return clientLoans.stream().map(ClientLoan::getClient).collect(Collectors.toList());}
}
