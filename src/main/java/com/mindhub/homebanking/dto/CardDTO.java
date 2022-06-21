package com.mindhub.homebanking.dto;


import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDTO {
    private long id;

    private String cardholder;

    private LocalDateTime fromDate,thruDate;

    private String number;
    private int cvv;
    private CardColor color ;

    private CardType type;

    public CardDTO(){}

    public CardDTO(Card card){
        this.id = card.getId();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.color= card.getColor();
        this.type = card.getType();
        this.cardholder = card.getCardholder();
    }

    public long getId() {return id;}

    public String getCardholder() {return cardholder;}

    public LocalDateTime getFromDate() {return fromDate;}

    public LocalDateTime getThruDate() {return thruDate;}

    public String getNumber() {return number;}

    public int getCvv() {return cvv;}

    public CardColor getColor() {return color;}

    public CardType getType() {return type;}
}
