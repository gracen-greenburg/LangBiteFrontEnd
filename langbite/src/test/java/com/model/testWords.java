package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;
public class testWords {
    // Tests to see if only one instance of a word is created, like in a singleton pattern
    @Test
    public void testSingletonInstance() {
    Words instance1 = Words.getInstance();
    Words instance2 = Words.getInstance();

    assertSame(instance1, instance2); // Both references should point to the same instance
    }

    // Test to make sure that the words list is empty at first
    @Test
    public void testGetWordsInitiallyEmpty() {
        Words wordsInstance = Words.getInstance();
        ArrayList<Word> wordsList = wordsInstance.getWords();

        assertTrue(wordsList.isEmpty());
    }

    // Tests to make sure that when a word is added to the list it is actually there
    @Test
    public void testAddWordToWordList() {
        Words wordsInstance = Words.getInstance();
        ArrayList<Word> wordsList = wordsInstance.getWords();
        Word word = new Word("hello", "bonjour", "masculine", "image.png", false, 0, 0, "noun", "core");

        wordsList.add(word); 

        assertTrue(wordsList.contains(word));
    }

    // Tests to make sure that a word can be removed from the word list
    @Test
    public void testRemoveWordfromWordList() {
        Words wordsInstance = Words.getInstance();
        Word word = new Word("hello", "bonjour", "masculine", "image.png", false, 0, 0, "noun", "core");
        wordsInstance.getWords().add(word);

        wordsInstance.getWords().remove(word);
        assertFalse(wordsInstance.getWords().contains(word));
    }

    // Tests if the word list can be emptied and return an empty set afterwards
    @Test
    public void testClearWordList() {
        Words wordsInstance = Words.getInstance();
        Word word = new Word("hello", "bonjour", "masculine", "image.png", false, 0, 0, "noun", "core");
        wordsInstance.getWords().add(word);

        wordsInstance.getWords().clear();
        assertTrue(wordsInstance.getWords().isEmpty());
    }

    // Tests if duplicate words are removed from the list
    @Test
    public void testDuplicateWords() {
        Words wordsInstance = Words.getInstance();
        Word word = new Word("hello", "bonjour", "masculine", "image.png", false, 0, 0, "noun", "core");

        wordsInstance.getWords().add(word);
        wordsInstance.getWords().add(word); // Add duplicate, should not remain in list

        assertEquals(1, wordsInstance.getWords().size()); // there should only be one word in list
        assertEquals(word, wordsInstance.getWords().get(0)); 
    }

    // Tests that word list should NOT accept null entures
    @Test
    public void testPreventNullEntriesInWordList() {
        Words wordsInstance = Words.getInstance();

        try {
            wordsInstance.getWords().add(null);
            fail("Expected NullPointerException or IllegalArgumentException");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected exception
        }

        assertTrue(wordsInstance.getWords().isEmpty()); // Nothing should be added // removed from the list
    }

}
