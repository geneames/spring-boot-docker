package io.sema.shuffle.service.impl;

import io.sema.shuffle.App;
import io.sema.shuffle.model.Deck;
import io.sema.shuffle.model.DeckFactory;
import io.sema.shuffle.model.DeckListVO;
import io.sema.shuffle.persistence.DeckRepository;
import io.sema.shuffle.service.DeckService;
import io.sema.shuffle.shuffler.Shuffler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = App.class)
@Import(DeckServiceTestConfiguration.class)
@ActiveProfiles("test")
class DeckServiceImplTest {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private DeckService deckService;

    @BeforeEach
    void setUp() {
        when(this.deckRepository.save(any(Deck.class)))
                .thenReturn(DeckFactory.newInstance("test-deck"));

        when(this.deckRepository.existsById(any(String.class)))
                .thenReturn(true);

        doNothing()
                .when(this.deckRepository).deleteById(any(String.class));

        when(this.deckRepository.findAll())
                .thenReturn(this.deckList());

        when(this.deckRepository.findById(any(String.class)))
                .thenReturn(Optional.of(DeckFactory.newInstance("test-deck")));
    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void createDeck() {
        Deck returnedDeck = this.deckService.createDeck("test-deck");
        assertNotNull(returnedDeck);
    }

    @Test
    void deckExists() {
        boolean retVal = this.deckService.deckExists("test-deck");
    }

    @Test
    void getDeckList() {
        List<DeckListVO> deckListVOS = this.deckService.getDeckList();
        assertEquals(1, deckListVOS.size());
    }

    @Test
    void getDeck() {
        Deck testDeck = this.deckService.getDeck("test-deck");
        assertEquals("test-deck", testDeck.getDeckName());
    }

    @Test
    void deleteDeck() {
        this.deckService.deleteDeck("deck-name");
    }

    @Test
    void shuffleDeck() {
        Deck testDeck = this.deckService.shuffleDeck("test-deck");
        assertEquals("test-deck", testDeck.getDeckName());
    }

    private List<Deck> deckList(){
        List<Deck> deckList = new ArrayList<>();

        deckList.add(DeckFactory.newInstance("test-deck"));

        return deckList;
    }
}