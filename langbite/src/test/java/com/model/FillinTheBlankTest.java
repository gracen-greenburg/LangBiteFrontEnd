package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FillinTheBlankTest {
    private FillinTheBlank fillinTheBlank;
    private List<String> correctAnswers;

    @Before
    public void setUp() {
        correctAnswers = Arrays.asList("quick", "brown", "fox");
        fillinTheBlank = new FillinTheBlank("The ___ ___ ___ jumps over the lazy dog.", correctAnswers);
    }

    // Test if constructor initializes correctly
    @Test
    public void testConstructorInitializesCorrectly() {
        assertEquals("The ___ ___ ___ jumps over the lazy dog.", fillinTheBlank.getSentenceWithBlanks());
        assertEquals(correctAnswers, fillinTheBlank.getCorrectAnswers());
    }

    // Test display method visually (optional, for manual checking)
    @Test
    public void testDisplay() {
        fillinTheBlank.display(); // Check console output for "The __1 __2 __3 jumps over the lazy dog."
    }

    // Test if submitAnswer assigns answer to correct blank index
    @Test
    public void testSubmitAnswerValidInput() {
        fillinTheBlank.submitAnswer(0, "quick");
        assertEquals("quick", fillinTheBlank.getUserAnswers().get(0));
    }

    // Test submitAnswer with an out-of-range index
    @Test
    public void testSubmitAnswerInvalidBlankIndex() {
        fillinTheBlank.submitAnswer(5, "dog");
        assertFalse(fillinTheBlank.getUserAnswers().contains("dog")); // Should not change
    }

    // Test checkAnswers returns true when all answers are correct
    @Test
    public void testCheckAnswersAllCorrect() {
        fillinTheBlank.submitAnswer(0, "quick");
        fillinTheBlank.submitAnswer(1, "brown");
        fillinTheBlank.submitAnswer(2, "fox");
        assertTrue(fillinTheBlank.checkAnswers());
    }

    // Test checkAnswers returns false if any answer is incorrect
    @Test
    public void testCheckAnswersNotAllCorrect() {
        fillinTheBlank.submitAnswer(0, "slow");
        fillinTheBlank.submitAnswer(1, "brown");
        fillinTheBlank.submitAnswer(2, "fox");
        assertFalse(fillinTheBlank.checkAnswers());
    }

    // Test case insensitivity in submitAnswer
    @Test
    public void testSubmitAnswerCaseInsensitive() {
        fillinTheBlank.submitAnswer(0, "QuIcK");
        assertEquals("quick", fillinTheBlank.getUserAnswers().get(0));
    }

    // Test if getCorrectAnswers returns the expected correct answers
    @Test
    public void testGetCorrectAnswers() {
        assertEquals(correctAnswers, fillinTheBlank.getCorrectAnswers());
    }

    // Test if userAnswers list initializes as empty strings
    @Test
    public void testInitialUserAnswersAreEmpty() {
        for (String answer : fillinTheBlank.getUserAnswers()) {
            assertEquals("", answer);
        }
    }

    // Test that submitAnswer does not accept empty input for a blank
    @Test
    public void testSubmitAnswerRejectsEmptyInput() {
        fillinTheBlank.submitAnswer(0, "");
        assertEquals("", fillinTheBlank.getUserAnswers().get(0));
    }
}