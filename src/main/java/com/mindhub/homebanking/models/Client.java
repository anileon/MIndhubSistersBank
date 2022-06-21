package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private long id;
    private String firstName,lastName,email;

    private String password;

    @OneToMany(mappedBy="client",fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();  //HasSet:Para que se me genere un espacio en memoria en algun lado  (como crear un array?)

    @OneToMany(mappedBy ="client",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans= new HashSet<>();

    @OneToMany(mappedBy ="client",fetch = FetchType.EAGER)
    private Set<Card> cards= new HashSet<>();

    public Client(){}

    public Client(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public Set<Account> getAccounts() {return accounts;}


    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount (Account account){
        account.setClient(this);
        accounts.add(account);
    }

    public Set<ClientLoan> getClientLoan() {return clientLoans; }

    public void addClientLoan(ClientLoan clientLoan) {clientLoan.setClient(this); clientLoans.add(clientLoan);}

    public List<Loan>getLoans(){return clientLoans.stream().map(client -> client.getLoan()).collect(Collectors.toList());}

    public Set<Card> getCards (){return cards;}

    public void addCard(Card card){card.setClient(this);cards.add(card);}

    public String getFullName() {return firstName + " " + lastName;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}


