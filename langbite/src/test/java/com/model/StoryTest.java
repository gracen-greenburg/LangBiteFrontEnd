package com.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class StoryTest {
    private Story story;

    @Before
    public void setUp() {
        List<String> wordList = Arrays.asList("red", "grandmother", "food", "hello", "bed", "door", "house", "ears", "eyes", "teeth");
        String storyText = "Once upon a time there was a girl called Little Red Riding Hood...";
        story = new Story("Little Red Riding Hood", storyText, wordList);
    }

    // Test if Story constructor initializes title correctly
    @Test
    public void testConstructorInitializesTitle() {
        assertEquals("Little Red Riding Hood", story.getTitle());
    }

    // Test if Story constructor initializes text correctly
    @Test
    public void testConstructorInitializesText() {
        assertNotNull("Story text should not be null", story.getText());
        assertTrue("Story text should contain 'Once upon a time'", story.getText().contains("Once upon a time"));
    }

    // Test if Story constructor initializes word list correctly
    @Test
    public void testConstructorInitializesWordList() {
        List<String> expectedWords = Arrays.asList("red", "grandmother", "food", "hello", "bed", "door", "house", "ears", "eyes", "teeth");
        assertEquals(expectedWords, story.getWordList());
    }

    // Test if getWordList returns a non-null/accurate list of words
    @Test
    public void testGetWordListNotNull() {
        assertNotNull("Word list should not be null", story.getWordList());
    }

    // Test if getWordList contains a specific word
    @Test
    public void testWordListContainsSpecificWord() {
        assertTrue("Word list should contain 'red'", story.getWordList().contains("red"));
    }

    // Test if wordList is empty when initialized with an empty list
    @Test
    public void testEmptyWordList() {
        Story emptyWordListStory = new Story("Empty Story", "This story has no keywords.", Arrays.asList());
        assertTrue("Word list should be empty", emptyWordListStory.getWordList().isEmpty());
    }

    // Test if the Story can handle special characters in text
    @Test
    public void testSpecialCharactersInText() {
        String specialStoryText = "Hickory Dickory Dock! The clock struck one.\n";
        Story specialStory = new Story("Hickory Dickory Dock", specialStoryText, Arrays.asList("clock", "one"));
        assertEquals(specialStoryText, specialStory.getText());
    }

    // Test if Story title is not null after construction
    @Test
    public void testTitleNotNull() {
        assertNotNull("Story title should not be null", story.getTitle());
    }

    // Test if Story text is correctly stored and retrieved
    @Test
    public void testGetText() {
        assertEquals("Once upon a time there was a girl called Little Red Riding Hood...", story.getText());
    }

    // Test if getWordList correctly returns all words in the list
    @Test
    public void testGetAllWords() {
        List<String> expectedWords = Arrays.asList("red", "grandmother", "food", "hello", "bed", "door", "house", "ears", "eyes", "teeth");
        assertEquals(expectedWords, story.getWordList());
    }
}