package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class ClientDTO {
  private String firstName,lastName,email;

  private long id;
  private Set<AccountsDTO> accounts = new HashSet<>();
  private Set<ClientLoanDTO> loans = new HashSet<>();
  private Set<CardDTO> cards = new HashSet<>();


  public ClientDTO (){}
  public ClientDTO (Client client){
    this.id = client.getId();
    this.firstName = client.getFirstName();
    this.lastName = client.getLastName();
    this.email = client.getEmail();

    this.accounts = client.getAccounts().stream().map(account -> new AccountsDTO(account)).collect(Collectors.toSet());

    this.loans = client.getClientLoan().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toSet());

    this.cards = client.getCards().stream().map(card-> new CardDTO(card)).collect(Collectors.toSet());
  }


  public long getId(){return id;}

  public String getFirstName(){return firstName;}

  public String getLastName(){return lastName;}

  public String getEmail(){return email;}

  public Set<AccountsDTO> getAccounts() {
    return accounts;
  }

  public Set<ClientLoanDTO> getLoans() {return loans;}

  public Set<CardDTO> getCards() {return cards;}
}



