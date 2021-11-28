package io.sema.shuffle;

import io.sema.shuffle.shuffler.Shuffler;
import io.sema.shuffle.shuffler.impl.InterleaveShuffler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("interleave-shuffle")
public class InterleaveShufflerConfig {

    @Bean
    public Shuffler shuffler(){
        return new InterleaveShuffler();
    }
}
