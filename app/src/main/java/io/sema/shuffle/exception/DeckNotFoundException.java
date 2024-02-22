package io.sema.shuffle.exception;

public class DeckNotFoundException extends RuntimeException {

    final private String notFoundDeckName;

    public DeckNotFoundException(String notFoundDeckName){
        this.notFoundDeckName = notFoundDeckName;
    }

    @Override
    public String getMessage() {
        return String.format("Deck named '%s' does not exist in database.", this.notFoundDeckName);
    }
}
