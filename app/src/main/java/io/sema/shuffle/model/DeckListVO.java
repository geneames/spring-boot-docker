package io.sema.shuffle.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeckListVO {

    private String deckName;

    public DeckListVO(String deckName){
        this.deckName = deckName;
    }

}
