package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")

    private long id;

    private String cardholder;

    private LocalDateTime fromDate,thruDate;

    private String number;
    private int cvv;
    private CardColor color ;
    private CardType type;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "client_id")
    private Client client;

    public  Card (){}

    public Card (CardType type, CardColor color, Client client, LocalDateTime fromDate, LocalDateTime thruDate, String number, int cvv) {
        this.client = client;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.number = number;
        this.cvv = cvv;
        this.color= color;
        this.type = type;
        this.cardholder = client.getFullName();
    }

    public long getId() {return id;}

    public String getCardholder() {return cardholder;}

    public void setCardholder(String cardholder) {this.cardholder = cardholder;}

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {return cvv;}

    public void setCvv(int cvv) {this.cvv = cvv;}

    public CardColor getColor() {return color;}

    public void setColor(CardColor color) {this.color = color;}

    public CardType getType() {return type;}

    public void setType(CardType type) {this.type = type;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}
}
