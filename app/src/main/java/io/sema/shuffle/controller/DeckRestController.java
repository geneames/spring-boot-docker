package io.sema.shuffle.controller;

import io.sema.shuffle.model.Deck;
import io.sema.shuffle.model.DeckListVO;
import io.sema.shuffle.service.DeckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoint that exposes APIs for the creation,
 * retrieval, shuffling, and removal of card decks.
 *
 * NOTE: This implementations follows provided
 * specification. However, the author takes issue
 * with the the implementation of the POST and PUT methods.
 * Generally, POST is for creation and PUT is for
 * updates. The spec provides POST method to shuffle
 * a given deck. Obviously, this modifies the state
 * of an existing resource or model. Conversely, the
 * spec provides PUT to idempotently create a new deck.
 * Convention dictates PUT is to modify the state of an
 * existing resource or model; not create a new one.
 *
 * All CRUD operations persist new state to persistent
 * storage.
 */
@RestController
@RequestMapping("/api/decks")
public class DeckRestController {

    private final Logger logger = LoggerFactory.getLogger(DeckRestController.class);
    final private DeckService deckService;

    public DeckRestController(DeckService deckService){
        this.deckService = deckService;
    }

    /**
     * Creates a new card deck with the given
     * name as the key. The initial order is
     * the same a new deck in a box of cards.
     *
     * @param deckName
     * @return Deck
     */
    @RequestMapping(value="{deckName}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteDeck(@PathVariable("deckName") String deckName){

        if(this.deckService.deckExists(deckName)) {
            this.logger.debug("Delete Deck {}", deckName);
            this.deckService.deleteDeck(deckName);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "{deckName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Deck> createNewDeck(@PathVariable("deckName") String deckName){
        Deck responseDeck;
        ResponseEntity<Deck> response;

        this.logger.debug("Create Deck {}", deckName);
        if(!deckService.deckExists(deckName)) {
            responseDeck = this.deckService.createDeck(deckName);
            response = new ResponseEntity<>(responseDeck, HttpStatus.CREATED);
        }
        else{
            responseDeck = this.deckService.getDeck(deckName);
            response = new ResponseEntity<>(responseDeck, HttpStatus.OK);
        }

        return response;
    }

    /**
     * Shuffles and returns the named deck.
     *
     * @param deckName
     * @return Deck
     */
    @PostMapping(value = "/{deckName}/shuffle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> shuffleDeck(@PathVariable("deckName") String deckName){

        ResponseEntity<?> response;

        if(this.deckService.deckExists(deckName)) {
            Deck shuffledDeck = this.deckService.shuffleDeck(deckName);
            response = new ResponseEntity<Deck>(shuffledDeck, HttpStatus.OK);
        }
        else{
            response = new ResponseEntity<String>("{}", HttpStatus.OK);
        }

        logger.debug("Deck '{}' shuffled", deckName);
        return response;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeckListVO>> getDeckNames(){
        List<DeckListVO> decks = this.deckService.getDeckList();
        return new ResponseEntity<>(decks, HttpStatus.OK);
    }

    @GetMapping(value = "{deckName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDeck(@PathVariable("deckName") String deckName){
        logger.debug("Getting deck {}", deckName);
        ResponseEntity<?> response;

//        if(this.deckService.deckExists(deckName)) {
            Deck retrievedDeck = this.deckService.getDeck(deckName);
            response = new ResponseEntity<Deck>(retrievedDeck, HttpStatus.OK);
//        }
//        else{
//            response = new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
//        }

        return response;
    }
}
