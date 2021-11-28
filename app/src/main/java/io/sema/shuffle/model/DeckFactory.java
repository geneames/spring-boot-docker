package io.sema.shuffle.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DeckFactory {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeckFactory.class);
    private final static String SUITS[] = {"Spades","Diamonds","Clubs","Hearts"};
    private final static String FACES[] = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};


    /**
     * Builds a new card deck in face-down order.
     *
     * If you think of a deck of cards as a stack
     * with a pop operation, the first card off
     * the stack is K-Hearts. The last card is the
     * A-Spaces.
     *
     * @param name
     * @return Deck of cards
     */
    public static Deck newInstance(String name){
        LOGGER.debug("Creating new instance of card deck named '{}'.", name);

        Deck newDeck = new Deck();
        newDeck.setName(name);

        // Add Cards to Deck
        Card newCard = null;
        for(String suit : SUITS){
            for(String face : FACES){
                newCard = new Card();
                newCard.setSuit(suit);
                newCard.setFace(face);

                // Add Card to Deck
                newDeck.addCard(newCard);
             }
        }
        return newDeck;
    }
}
