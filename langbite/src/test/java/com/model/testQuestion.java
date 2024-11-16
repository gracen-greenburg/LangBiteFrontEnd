package com.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class testQuestion {
    private Question question;

    @Before
    public void setUp() {
        question = new Question("What is the capital of France?", "Paris", "MultipleChoice");
    }

    // Tests that the correct question can get returned
    @Test
    public void testGetQuestionText() {
        assertEquals("What is the capital of France?", question.getQuestionText());
    }

    //Tests if submitting answser to question works
    @Test
    public void testSubmitAnswer() {
        question.submitAnswer("Paris");
        assertEquals("Paris", question.getUserAnswer());
    }

    // Tests if the correct answer is correct when submitted
    @Test
    public void testAnswerCorrect() {
        question.submitAnswer("Paris");
        assertTrue("Expected answer to be correct", question.checkAnswer());
    }

    // Tests if an incorrect answer is incorrect when submitted
    @Test
    public void testAnswerIncorrect() {
        question.submitAnswer("London");
        assertFalse("Expected answer to be incorrect", question.checkAnswer());
    }

    // Tests if the score is updated properly when correct
    @Test
    public void testeScoreCorrectAnswer() {
        question.submitAnswer("Paris");
        assertEquals("Expected score to be 1 for correct answer", 1, question.calculateScore());
    }

    // Tests if score is updated when it is not
    @Test
    public void testScoreIncorrectAnswer() {
        question.submitAnswer("London");
        assertEquals("Expected score to be 0 for incorrect answer", 0, question.calculateScore());
    }

    // Tests if the question resets properly
    @Test
    public void testResetQuestion() {
        question.submitAnswer("Paris");
        question.calculateScore();
        question.resetQuestion();

        assertEquals("Expected userAnswer to be empty ", "", question.getUserAnswer());
        assertEquals("Expected score to be 0", 0, question.getScore());
    }

    // Tests if correct when casing different
    @Test
    public void testAnswerCaseInsensitive() {
        question.submitAnswer("paris");
        assertTrue("Expected answer to be correct with different case", question.checkAnswer());
    }

    // Tests if correct if the answer submitted has whitespace
    @Test
    public void testCheckAnswerWithWhitespace() {
        question.submitAnswer("  Paris  ");
        assertTrue("Expected answer to be correct with whitespace", question.checkAnswer());
    }

    // Tests what happens when an empty answer is submitted
    @Test
    public void testSubmitEmptyAnswer() {
        question.submitAnswer("");
        assertFalse("Expected checkAnswer to return false", question.checkAnswer());
        assertEquals("Expected score to be 0", 0, question.calculateScore());
    }

    // Tests what happens when a null answer is submitted
    @Test
    public void testSubmitNullAnswer() {
        question.submitAnswer(null);
        assertFalse("Expected checkAnswer to return falser", question.checkAnswer());
        assertEquals("Expected score to be 0", 0, question.calculateScore());
    }

    // Testing resetting after an incorrect answer
    @Test
    public void testResetAfterIncorrectAnswer() {
        question.submitAnswer("Berlin");  // Incorrect
        question.calculateScore();
        assertEquals("Expected score to be 0", 0, question.calculateScore());
        
        question.resetQuestion(); // resetting question
        assertEquals("Expected score to be reset to 0", 0, question.calculateScore());
        assertEquals("Expected userAnswer to be empty", "", question.getUserAnswer());
    }

    // Tests if partial answers are accepted
    @Test
    public void testPartialAnswer() {
        question.submitAnswer("Par");  // Partial match
        assertFalse("Expected checkAnswer to return false", question.checkAnswer());
    }

    // Testing if it can handle multiple questions at once
    @Test
    public void testMultipleSubmissions() {
        question.submitAnswer("London");  // Incorrect
        assertEquals("Expected score to be 0", 0, question.calculateScore());

        question.submitAnswer("Paris");  // Correct answer on second attempt
        assertTrue("Expected checkAnswer to return true", question.checkAnswer());
        assertEquals("Expected score to be 1", 1, question.calculateScore());
    }
}
