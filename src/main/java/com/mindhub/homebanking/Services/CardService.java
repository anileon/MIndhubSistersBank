package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.Card;


import java.util.List;

public interface CardService {

    List<CardDTO> getCards();

    void saveCard(Card card);
}
