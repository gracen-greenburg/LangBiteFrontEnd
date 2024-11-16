package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import com.model.Flashcard.Flashcard;
import com.model.Flashcard.Flashcards;

import java.util.Arrays;
import java.util.List;

public class FlashcardsTest {
    private Flashcards flashcards;

    @Before
    public void setUp() {
        // Sample flashcard list for testing
        List<Flashcard> flashcardList = Arrays.asList(
            new Flashcard("Apple", "Pomme"),
            new Flashcard("Dog", "Chien"),
            new Flashcard("House", "Maison")
        );
        flashcards = new Flashcards(flashcardList);
    }

    @Test
    public void testShowFlashcard() {
        // Initial flashcard (English first)
        flashcards.showFlashcard();
        assertEquals("Apple", flashcards.getCurrentFlashcard().getEnglishWord());
    }

    @Test
    public void testRevealTranslation() {
        flashcards.revealTranslation();
        // Checking that the translation for the first flashcard shows the French word
        assertEquals("Pomme", flashcards.getCurrentFlashcard().getFrenchWord());
    }

    @Test
    public void testNextFlashcard() {
        // Move to next flashcard and check that index increments correctly
        flashcards.nextFlashcard();
        assertEquals("Dog", flashcards.getCurrentFlashcard().getEnglishWord());
    }

    @Test
    public void testPreviousFlashcard() {
        flashcards.nextFlashcard(); // Move forward once
        flashcards.previousFlashcard(); // Move back to the first card
        assertEquals("Apple", flashcards.getCurrentFlashcard().getEnglishWord());
    }

    @Test
    public void testToggleFrontLanguage() {
        flashcards.toggleFrontLanguage();
        assertEquals("Pomme", flashcards.getCurrentFlashcard().displayFlashcard(false));
        flashcards.toggleFrontLanguage();
        assertEquals("Apple", flashcards.getCurrentFlashcard().displayFlashcard(true));
    }

    @Test
    public void testShuffleFlashcards() {
        // Shuffle flashcards and check that they are in a different order
        List<Flashcard> originalOrder = flashcards.getFlashcardsList();
        flashcards.shuffleFlashcards();
        assertFalse(originalOrder.equals(flashcards.getFlashcardsList()));
    }
}