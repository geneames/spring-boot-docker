package io.sema.shuffle.service.impl;

import io.sema.shuffle.exception.DeckNotFoundException;
import io.sema.shuffle.model.Deck;
import io.sema.shuffle.model.DeckFactory;
import io.sema.shuffle.model.DeckListVO;
import io.sema.shuffle.persistence.DeckRepository;
import io.sema.shuffle.service.DeckService;
import io.sema.shuffle.shuffler.Shuffler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This component manages all data operations.
 */
@Service
public class DeckServiceImpl implements DeckService {

    final private Logger logger = LoggerFactory.getLogger(DeckService.class);
    final private Shuffler shuffler;
    final private DeckRepository deckRepo;

    public DeckServiceImpl(
            Shuffler shuffler,
            DeckRepository deckRepo){

        this.shuffler = shuffler;
        this.deckRepo = deckRepo;
    }

    @Override
    public Deck createDeck(String deckName) {
        Deck newDeck = DeckFactory.newInstance(deckName);

        return this.deckRepo.save(newDeck);
    }

    @Override
    public boolean deckExists(String deckName) {
        return this.deckRepo.existsById(deckName);
    }

    @Override
    public List<DeckListVO> getDeckList() {
        List<Deck> deckList = this.deckRepo.findAll();
        List<DeckListVO> deckNameList = new ArrayList<>();

        for(Deck deck : deckList){
            deckNameList.add(new DeckListVO(deck.getDeckName()));
        }

        return deckNameList;
    }

    @Override
    public Deck getDeck(String deckName) {
        Optional<Deck> deckOptional = this.deckRepo.findById(deckName);
        return deckOptional.orElseThrow(() -> new DeckNotFoundException(deckName));
    }

    @Override
    public void deleteDeck(String deckName) {
        this.deckRepo.deleteById(deckName);
    }

    @Override
    public Deck shuffleDeck(String deckName) {
        this.logger.debug("Shuffling deck {}", deckName);

        // Retrieve Deck
        Deck deck = this.getDeck(deckName);

        // Retrieve and Shuffle deck
        this.shuffler.shuffle(deck);

        // Persist shuffled deck
        return this.deckRepo.save(deck);
    }
}
