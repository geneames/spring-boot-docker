package io.sema.shuffle.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class DeckListVO {

    private String deckName;

    public DeckListVO(String deckName){
        this.deckName = deckName;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
