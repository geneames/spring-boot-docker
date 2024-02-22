package io.sema.shuffle.service;


import io.sema.shuffle.model.Deck;
import io.sema.shuffle.model.DeckListVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DeckService {

    Deck shuffleDeck(String deckName);
    Deck createDeck(String deckName);
    List<DeckListVO> getDeckList();
    Deck getDeck(String deckName);
    void deleteDeck(String deckName);
    boolean deckExists(String deckName);
}
