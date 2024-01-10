package io.sema.shuffle.model;


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
