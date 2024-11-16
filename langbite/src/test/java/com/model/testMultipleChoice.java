package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class testMultipleChoice {

    private MutipleChoice multipleChoiceQuestion;

    @Before
    public void setUp() {
        // Initialize a MultipleChoice instance with a sample question, options, and correct answer
        List<String> options = List.of("Lyon", "Paris", "Marseille", "Nice");
        multipleChoiceQuestion = new MutipleChoice("What is the capital of France?", options, 'B');
    }

    // Test that the constructor initializes the question, options, and correct answer correctly
    @Test
    public void testConstructor() {
        assertEquals("What is the capital of France?", multipleChoiceQuestion.question);
        assertEquals(List.of("Lyon", "Paris", "Marseille", "Nice"), multipleChoiceQuestion.options);
        assertEquals('B', multipleChoiceQuestion.getCorrectAnswer());
    }

    // Test the submitAnswer method with the correct answer
    @Test
    public void testSubmitAnswer_Correct() {
        assertTrue("The answer should be correct", multipleChoiceQuestion.submitAnswer('B'));
    }

    // Test the submitAnswer method with an incorrect answer
    @Test
    public void testSubmitAnswer_Incorrect() {
        assertFalse("The answer should be incorrect", multipleChoiceQuestion.submitAnswer('A'));
    }

    // Test the submitAnswer method with a lowercase correct answer
    @Test
    public void testSubmitAnswer_LowercaseCorrect() {
        assertTrue("The answer should be correct even if lowercase", multipleChoiceQuestion.submitAnswer('b'));
    }

    // Test the submitAnswer method with an invalid choice (not in options range)
    @Test
    public void testSubmitAnswer_InvalidChoice() {
        assertFalse("An invalid choice should return false", multipleChoiceQuestion.submitAnswer('E'));
    }

    // Test the display method to ensure it runs without errors
    @Test
    public void testDisplay() {
        // Since display only prints, we'll verify it runs without error
        multipleChoiceQuestion.display();
    }
}
