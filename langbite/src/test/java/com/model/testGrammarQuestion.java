package com.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class testGrammarQuestion {

    private GrammarQuestion grammarQuestion;
    private String text;
    private List<String> options;
    private int correctAnswer;
    private String type;
    private String expectedAnswer;

    @Before
    public void setUp() {
        // Initialize test data
        text = "What is the plural of cat (chat)?";
        options = Arrays.asList("chate", "chates", "chats", "chat");
        correctAnswer = 3;
        type = "multiple-choice";
        expectedAnswer = "chats";

        // Create GrammarQuestion instance with test data
        grammarQuestion = new GrammarQuestion(text, options, correctAnswer, type, expectedAnswer);
    }

    // Test that the constructor initializes all fields correctly
    @Test
    public void testConstructor() {
        assertEquals(text, grammarQuestion.getText());
        assertEquals(options, grammarQuestion.getOptions());
        assertEquals(correctAnswer, grammarQuestion.getCorrectAnswer());
        assertEquals(type, grammarQuestion.getType());
        assertEquals(expectedAnswer, grammarQuestion.getExpectedAnswer());
    }

    // Test the getText method
    @Test
    public void testGetText() {
        assertEquals("What is the plural of cat (chat)?", grammarQuestion.getText());
    }

    // Test the getOptions method
    @Test
    public void testGetOptions() {
        List<String> expectedOptions = Arrays.asList("chate", "chates", "chats", "chat");
        assertEquals(expectedOptions, grammarQuestion.getOptions());
    }

    // Test the getCorrectAnswer method
    @Test
    public void testGetCorrectAnswer() {
        assertEquals(3, grammarQuestion.getCorrectAnswer());
    }

    // Test the getType method
    @Test
    public void testGetType() {
        assertEquals("multiple-choice", grammarQuestion.getType());
    }

    // Test the getExpectedAnswer method
    @Test
    public void testGetExpectedAnswer() {
        assertEquals("chats", grammarQuestion.getExpectedAnswer());
    }
}
