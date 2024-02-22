package io.sema.shuffle.shuffler.impl;

import io.sema.shuffle.model.Card;
import io.sema.shuffle.model.Deck;
import io.sema.shuffle.model.DeckFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomShufflerTest {

    /**
     * Testing shuffle of a "new" deck, which means
     * to imply deck came from a newly opened "box".
     *
     * Example:
     * From the bottom A-Spades, 2-Spades, 3-Spades, ...
     */
    @Test
    @DisplayName("Compare Card Order After Shuffle of New Deck")
    void testShuffleNewDeck(){
        Deck newDeck = DeckFactory.newInstance("new-test-deck");
        Deck newDeckClone = newDeck.clone();

        assertThat("Cloned deck should not have a name", newDeckClone.getDeckName(), emptyOrNullString());
        assertEquals(newDeck.getCards().size(), newDeckClone.getCards().size(), "New deck and clone should have 52 cards");

        // Verify Card Order Matches
        for(int i = 0; i < 52; i++){
            Card newDeckCard = newDeck.getCards().get(i);
            Card newDeckCardClone = newDeckClone.getCards().get(i);

            assertEquals(newDeckCard.faceValue(), newDeckCardClone.faceValue(), "Each card should have same face value");
        }

        RandomShuffler shuffler = new RandomShuffler();
        shuffler.shuffle(newDeck);
        // Verify Card Order Don't Match
        int numberOfComparisonMatches = 0;

        for(int i = 0; i < 52; i++){
            Card newDeckCard = newDeck.getCards().get(i);
            Card newDeckCardClone = newDeckClone.getCards().get(i);

            if(newDeckCard.faceValue().equals(newDeckCardClone.faceValue())){
                numberOfComparisonMatches++;
            }
        }

        assertThat("The degree of randomness isn't high enough", (numberOfComparisonMatches / 52.0), lessThan(0.1));
    }

    /**
     * Testing shuffle of a previously shuffled or
     * used deck.
     *
     * Example:
     * From the bottom K-Spades, 2-Hearts, 3-Clubs, ...
     */
    @Test
    @DisplayName("Compare Card Order After Shuffle of Previously Shuffled Deck")
    void testShuffleUsedDeck(){
        Deck newDeck = DeckFactory.newInstance("new-test-deck");
        RandomShuffler shuffler = new RandomShuffler();

        // Shuffle then clone new deck
        shuffler.shuffle(newDeck);
        Deck newDeckClone = newDeck.clone();

        assertThat("Cloned deck should not have a name", newDeckClone.getDeckName(), emptyOrNullString());

        // Verify Card Order Matches
        for(int i = 0; i < 52; i++){
            Card newDeckCard = newDeck.getCards().get(i);
            Card newDeckCardClone = newDeckClone.getCards().get(i);

            assertEquals(newDeckCard.faceValue(), newDeckCardClone.faceValue(), "Each card should have same face value");
        }

        // Shuffle Again
        shuffler.shuffle(newDeck);

        // Verify Card Order Don't Match
        int numberOfComparisonMatches = 0;

        for(int i = 0; i < 52; i++){
            Card newDeckCard = newDeck.getCards().get(i);
            Card newDeckCardClone = newDeckClone.getCards().get(i);

            if(newDeckCard.faceValue().equals(newDeckCardClone.faceValue())){
                numberOfComparisonMatches++;
            }
        }

        assertThat("The degree of randomness isn't high enough", (numberOfComparisonMatches / 52.0), lessThan(0.1));
    }
}