package io.sema.shuffle.shuffler.impl;

import io.sema.shuffle.model.Card;
import io.sema.shuffle.model.Deck;
import io.sema.shuffle.shuffler.Shuffler;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Using a divide and conquer method, this interleaves
 * shuffle algorithm simulates a perfect
 * hand shuffle, taking a card from half of
 * the deck and interlacing it with a card
 * from the other half of the deck. The number
 * of shuffles is randomly determined between
 * 25,000 and 50,000 times.
 *
 * The flaw is, if it was known how many shuffles
 * of the deck were applied, a state of the deck
 * could be easily reproduced. To counter this, a
 * binary switch is randomly generated. This switch
 * is used to determine which half of the deck starts
 * the shuffle process.
 */

@Component
@Primary
@Profile("interleave-shuffle")
public class InterleaveShuffler implements Shuffler {

    @Override
    public void shuffle(Deck deck) {
        // Get Cards from Deck
        List<Card> cards = deck.getCards();
        List<Card> shuffledCards = new ArrayList<>();

        // Random Shuffle Count
        int shuffleIterations = ThreadLocalRandom.current().nextInt(25000, 50000);

        for(int shuffleCounter = 0; shuffleCounter <= shuffleIterations; shuffleCounter++) {
            /*
             * A flag to indicate whether to start from the bottom of
             * the deck or the middle of the deck.
             *
             * 0 for bottom or 1 the middle.
             */
            int startFlag = ThreadLocalRandom.current().nextInt(0, 2);

            // Clear Shuffled Deck list
            shuffledCards.clear();
            for (int i = 0; i < 26; i++) {
                if(startFlag == 0) {
                    shuffledCards.add(cards.get(i));
                    shuffledCards.add(cards.get(i + 26));
                }
                else{
                    shuffledCards.add(cards.get(i + 26));
                    shuffledCards.add(cards.get(i));
                }
            }
            cards = new ArrayList<>(shuffledCards);
        }
        deck.setCards(shuffledCards);
    }
}
