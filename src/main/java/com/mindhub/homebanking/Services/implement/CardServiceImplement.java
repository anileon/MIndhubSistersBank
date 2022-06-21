package com.mindhub.homebanking.Services.implement;

import com.mindhub.homebanking.Repositories.CardRepository;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    CardRepository cardRepository;

    @Override
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(toList());
    }
    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
