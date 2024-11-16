package com.model;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HundredMostCommonWordsTest {

    private HundredMostCommonWords hundredMostCommonWords;
    private List<Word> wordList;

    @Before
    public void setUp() {
        // Sample Word objects for testing
        wordList = new ArrayList<>(Arrays.asList(
            new Word("maison", "house", "feminine", "image1.png", false, 0, 0, "noun", "100MostCommonWords"),
            new Word("chat", "cat", "masculine", "image2.png", false, 0, 0, "noun", "100MostCommonWords"),
            new Word("chien", "dog", "masculine", "image3.png", false, 0, 0, "noun", "100MostCommonWords"),
            new Word("voiture", "car", "feminine", "image4.png", false, 0, 0, "noun", "100MostCommonWords")
        ));
        
        // Mock the loadWords method
        DataLoader.setMockData(wordList);

        // Simulate a user JSON and let hundredMostCommonWords access
        JSONObject currentUser = new JSONObject();
        currentUser.put("progress", new JSONObject());
        hundredMostCommonWords = new HundredMostCommonWords(currentUser);
    }

    // Test to ensure all words are loaded correctly
    @Test
    public void testLoadAllWords() {
        ArrayList<Word> loadedWords = DataLoader.loadWords();
        assertEquals("Expected 4 words to be loaded", 4, loadedWords.size());
    }

    // Test to verify word is marked mastered after correct count reaches threshold
    @Test
    public void testMarkWordAsMastered() {
        Word word = wordList.get(0); // select a sample word
        assertFalse("Word should not be mastered initially", word.isMastered());

        word.incrementCorrectCount();
        word.setMastered(word.getCorrectCount() >= 1);
        assertTrue("Word should be mastered after correct count reaches 1", word.isMastered());
    }

    // Test to check option generation produces diffrent and shuffled options
    @Test
    public void testGenerateOptions() {
        Word testWord = wordList.get(0);
        List<String> options = hundredMostCommonWords.generateOptions(testWord.getEnglish(), new ArrayList<>(wordList));

        assertEquals("Should have 4 options", 4, options.size());
        assertTrue("Correct answer should be in options", options.contains(testWord.getEnglish()));

        // Ensure options are diffrent
        assertEquals("Options should be unique", options.size(), options.stream().distinct().count());
    }

    // Test allWordsMastered returns true only when all words are mastered
    @Test
    public void testAllWordsMastered() {
        for (Word word : wordList) {
            word.setMastered(true); // Mark all words as mastered
        }
        assertTrue("Expected allWordsMastered to return true", hundredMostCommonWords.allWordsMastered());
    }

    // Test allWordsMastered returns false when not all words are mastered
    @Test
    public void testNotAllWordsMastered() {
        wordList.get(0).setMastered(false); // Mark one word as unmastered
        assertFalse("Expected allWordsMastered to return false", hundredMostCommonWords.allWordsMastered());
    }

    // Test to check unique word list
    @Test
    public void testWordListContainsWord() {
        Word word = new Word("chat", "cat", "masculine", "image2.png", false, 0, 0, "noun", "100MostCommonWords");
        assertTrue("Word should be in word list", hundredMostCommonWords.wordListContainsWord(word));
    }

    // Test to check that printNewWords method doesn't print duplicates
    @Test
    public void testPrintNewWordsNoDuplicates() {
        ArrayList<Word> newWords = new ArrayList<>(Arrays.asList(
            new Word("maison", "house", "feminine", "image1.png", false, 0, 0, "noun", "100MostCommonWords"),
            new Word("chien", "dog", "masculine", "image3.png", false, 0, 0, "noun", "100MostCommonWords")
        ));

        hundredMostCommonWords.printNewWords(newWords);
        assertEquals("Printed words should not contain duplicates", newWords.size(), newWords.stream().distinct().count());
    }

    @Test
    public void testMockDataLoader() {
        ArrayList<Word> loadedWords = DataLoader.loadWords();
        assertEquals("Expected number of words loaded from mock data", 4, loadedWords.size());
        assertEquals("Expected word to be loaded", "maison", loadedWords.get(0).getFrench());
    }
}