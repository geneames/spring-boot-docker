package io.sema.shuffle;


import io.sema.shuffle.shuffler.Shuffler;
import io.sema.shuffle.shuffler.impl.RandomShuffler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("random-shuffle")
public class RandomShufflerConfig {

    @Bean
    public Shuffler shuffler(){
        return new RandomShuffler();
    }
}
