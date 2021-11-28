package io.sema.shuffle.persistence;


import io.sema.shuffle.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, String> {
}
