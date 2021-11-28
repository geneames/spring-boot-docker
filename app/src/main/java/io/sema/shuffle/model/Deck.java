package io.sema.shuffle.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "DECKS")
public class Deck implements Serializable {

    @Id
    @Column(name = "deck_name", nullable = false, updatable = false)
    private String deckName;

    @OneToMany(mappedBy = "parentDeck", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderColumn(name = "order_idx")
    private List<Card> cards = new ArrayList<>();

    public List<Card> getCards(){
        return this.cards;
    }

    public void setCards(List<Card> cards){
        this.cards = cards;
    }

    public String getDeckName(){
        return this.deckName;
    }

    void addCard(Card newCard){
        newCard.setParentDeck(this);
        this.cards.add(newCard);
    }

    void setName(String deckName){
        this.deckName = deckName;
    }

    @Override
    public String toString() {
        return String.format("Deck Name: %s", this.deckName);
    }

    @Override
    public int hashCode() {
        return this.deckName.hashCode() + this.cards.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Deck extDeck = (Deck)obj;

        boolean isEqual =   extDeck.getCards().containsAll(this.cards) &&
                            extDeck.getDeckName().equals(this.deckName);
        return isEqual;
    }
}
