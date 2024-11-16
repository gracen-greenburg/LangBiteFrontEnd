package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class testWord {

    // Tests if all of the variables initialized correctly
    @Test
    public void testConstructor() {
        Word word = new Word("hello", "bonjour", "masculine", "image.png", true, 5, 10, "noun", "vocabulary");

        assertEquals("hello", word.getEnglish());
        assertEquals("bonjour", word.getFrench());
        assertEquals("masculine", word.getGender());
        assertEquals("image.png", word.getImage());
        assertTrue(word.isMastered());
        assertEquals(5, word.getCorrectCount());
        assertEquals(10, word.getAttempts());
        assertEquals("noun", word.getPartOfSpeech());
        assertEquals("vocabulary", word.getType());
    }

    // Tests if the getters work
    @Test
    public void testGetters() {
        Word word = new Word("run", "courir", "masculine", "image.png", false, 3, 8, "verb", "core");

        assertEquals("run", word.getEnglish());
        assertEquals("courir", word.getFrench());
        assertEquals("masculine", word.getGender());
        assertEquals("image.png", word.getImage());
        assertFalse(word.isMastered());
        assertEquals(3, word.getCorrectCount());
        assertEquals(8, word.getAttempts());
        assertEquals("verb", word.getPartOfSpeech());
        assertEquals("core", word.getType());
    }

    // Tests whether the differetn parts of speech and types register
    @Test
    public void testDifferentSpeechandTypes() {
        Word wordTest1 = new Word("hello", "bonjour", "masculine", "image.png", false, 0, 0, "noun", "vocabulary");
        Word wordTest2 = new Word("run", "courir", "none", "image2.png", true, 10, 20, "verb", "core");

        assertEquals("noun", wordTest1.getPartOfSpeech()); // part of speech should be noun
        assertEquals("vocabulary", wordTest1.getType()); // type should be vocabulary

        assertEquals("verb", wordTest2.getPartOfSpeech()); // part of speech should be verb
        assertEquals("core", wordTest2.getType()); // type should be core
    }

    // Tests if correct answer count increments correctly
    @Test
    public void testCorrectCount() {
        Word word = new Word("hello", "bonjour", "masculine", "image.png", false, 0, 0, "noun", "vocabulary");

        word.incrementCorrectCount();
        assertEquals(1, word.getCorrectCount()); // Incremented, expects correct count to go up

        word.incrementCorrectCount();
        assertEquals(2, word.getCorrectCount()); // Incremented, expects correct count to go up
    }

    //Tests if the setCorrectCount method works
    @Test
    public void testSetCorrectCount() {
        Word word = new Word("hello", "bonjour", "masculine", "image.png", false, 0, 0, "noun", "core");

        word.setCorrectCount(5); // Sets to 5
        assertEquals(5, word.getCorrectCount()); // Should be 5

        word.setCorrectCount(10); // Sets to 10
        assertEquals(10, word.getCorrectCount()); // Should be 10
    }

    // Tests edge case with invalid (negative) input
    @Test
    public void testNegativeConstructor() {
        Word word = new Word("hello", "bonjour", "masculine", "image.png", false, -1, -5, "noun", "vocabulary");

        assertEquals(-1, word.getCorrectCount());
        assertEquals(-5, word.getAttempts());
    }

    // Tests to see what happens when the word is initialized with empty strings
    @Test
    public void testWithEmptyStrings() {
        Word word = new Word("", "", "", "", false, 0, 0, "", "");

        assertEquals("", word.getEnglish());
        assertEquals("", word.getFrench());
        assertEquals("", word.getGender());
        assertEquals("", word.getPartOfSpeech());
        assertEquals("", word.getType());
    }

    // Tests to be sure that the correct count does not exceed the times attempted
    @Test
    public void testCorrectCountDoesNotExceedAttempts() {
        Word word = new Word("hello", "bonjour", "masculine", "image.png", false, 5, 5, "noun", "core");

        word.incrementCorrectCount();
        word.incrementAttempts();

        assertTrue(word.getCorrectCount() <= word.getAttempts()); // You can't get it correct more times then you've tried
    }

    // Tests to make sure that the french words with special letters translate into English and 
    // maintain their equality through translation
    @Test
    public void testSpecialLettersFromFrench() {
        Word word = new Word("café", "café", "masculine", "image.png", false, 0, 0, "noun", "vocabulary");

        assertEquals("café", word.getEnglish());
        assertEquals("café", word.getFrench());
    }

}
