package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.Repositories.CardRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.mindhub.homebanking.Utils.Utils.getRandomNumber;
import static com.mindhub.homebanking.models.CardType.CREDITO;
import static com.mindhub.homebanking.models.CardType.DEBITO;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")

public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/cards")
    public List<CardDTO> getCards(){
        return cardService.getCards();
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> cardCreated (Authentication authentication,
       @RequestParam CardColor color, @RequestParam CardType type) {

        Client client = clientService.getClientByEmail(authentication.getName()); //usuario autentificado

        List <Card> debitCard = client.getCards().stream().filter(debit -> debit.getType() == DEBITO).collect(toList());
        List <Card> creditCard = client.getCards().stream().filter(credit -> credit.getType() == CardType.CREDITO).collect(toList());

        if (type == DEBITO && debitCard.size() >= 3){
            return new ResponseEntity<>("You have reached the max number of cards available", HttpStatus.FORBIDDEN);
        }
        if (type == CREDITO && creditCard.size() >= 3){
            return new ResponseEntity<>("You have reached the max number of cards available", HttpStatus.FORBIDDEN);
        }
        String number =getRandomNumber(1000,9999) + "-" + getRandomNumber(1000,9999) + "-" + getRandomNumber(1000,9999) + "-" + getRandomNumber(1000,9999);

        int cvv = getRandomNumber(100,999);

        LocalDateTime fromDate = LocalDateTime.now();

        LocalDateTime thruDate = LocalDateTime.now().plusYears(5);

        String cardholder = client.getFullName();

       cardService.saveCard(new Card(type,color,client,fromDate,thruDate,number,cvv));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
