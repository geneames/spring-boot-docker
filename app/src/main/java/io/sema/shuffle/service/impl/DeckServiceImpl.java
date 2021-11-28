package io.sema.shuffle.service.impl;

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
 * This component manages all of the data operations.
 */
@Service
class DeckServiceImpl implements DeckService {

    final private Logger logger = LoggerFactory.getLogger(DeckService.class);

    @Autowired
    private Shuffler shuffler;

    @Autowired
    private DeckRepository deckRepo;

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
        return deckOptional.orElseGet(() -> this.deckRepo.findById(deckName).get());
    }

    @Override
    public void deleteDeck(String deckName) {
        this.deckRepo.deleteById(deckName);
    }

    @Override
    public Deck shuffleDeck(String deckName) {
        Deck deck = null;
        this.logger.debug("Shuffling deck {}", deckName);

        // Retrieve Deck
        if(this.deckRepo.existsById(deckName)) {
            deck = this.deckRepo.getById(deckName);
        }
        else {
            throw new RuntimeException(String.format("Deck named '%s' does not exist in database.", deckName));
        }

        // Shuffle deck
        this.shuffler.shuffle(deck);

        // Persist shuffled deck
        return this.deckRepo.save(deck);
    }
}
