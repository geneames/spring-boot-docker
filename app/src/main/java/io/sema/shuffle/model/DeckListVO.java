package io.sema.shuffle.model;


import com.fasterxml.jackson.annotation.JsonGetter;

public class DeckListVO {

    private String deckName;

    public DeckListVO(String deckName){
        this.deckName = deckName;
    }

    @JsonGetter
    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
