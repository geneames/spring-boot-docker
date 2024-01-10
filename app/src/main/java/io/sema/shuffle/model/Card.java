package io.sema.shuffle.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Entity(name = "CARDS")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"parent_deck_name","suit","face"}))
@EqualsAndHashCode
@ToString
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

    @JsonGetter
    public String faceValue(){
        return String.format("%s-%s", this.face, this.suit);
    }

     Card setParentDeck(Deck parentDeck){
        this.parentDeck = parentDeck;

         return this;
    }
}
