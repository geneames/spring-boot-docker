package io.sema.shuffle.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "DECKS")
@EqualsAndHashCode
@ToString
public class Deck implements Serializable {

    public Deck(){}

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

    public Deck clone() {
        Deck clonedDeck =  new Deck();

        for(Card card : this.cards){
            Card newCard = new Card();
            newCard.setFace(card.getFace());
            newCard.setSuit(card.getSuit());
            newCard.setParentDeck(this);

            clonedDeck.addCard(newCard);
        }

        return clonedDeck;
    }
}
