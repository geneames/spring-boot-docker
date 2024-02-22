package io.sema.shuffle.service.impl;

import io.sema.shuffle.persistence.DeckRepository;
import io.sema.shuffle.service.DeckService;
import io.sema.shuffle.shuffler.Shuffler;
import io.sema.shuffle.shuffler.impl.InterleaveShuffler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DeckServiceTestConfiguration {

    @MockBean
    DeckRepository deckRepository;

    @Bean
    Shuffler shuffler(){ return new InterleaveShuffler(); }

    @Bean
    DeckService deckService() { return new DeckServiceImpl(this.shuffler(), this.deckRepository); }
}
