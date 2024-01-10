package io.sema.shuffle.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "CARDS")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"parent_deck_name","suit","face"}))
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false, updatable = false)
    private long cardId;

    @Column(name = "suit", length = 8, nullable = false, updatable = false)
    private String suit;

    @Column(name = "face", length = 2, nullable = false, updatable = false)
    private String face;

    @ManyToOne
    @JoinColumn(name = "parent_deck_name", referencedColumnName = "deck_name", nullable = false)
    private Deck parentDeck;

    String getSuit() {
        return this.suit;
    }

    void setSuit(String suit) {
        this.suit = suit;
    }

    String getFace() {
        return this.face;
    }

    void setFace(String face) {
        this.face = face;
    }

    public String faceValue(){
        return String.format("%s-%s", this.face, this.suit);
    }

     Card setParentDeck(Deck parentDeck){
        this.parentDeck = parentDeck;

         return this;
    }

    @Override
    public int hashCode() {
        return  this.parentDeck.getDeckName().hashCode() + this.suit.hashCode() + this.face.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Card otherCard = (Card)obj;
        return this.toString().equals(otherCard.toString());
    }

    @Override
    public String toString() {
        return String.format("%s-%s:Deck(%s)", this.face, this.suit, this.parentDeck.getDeckName());
    }
}
