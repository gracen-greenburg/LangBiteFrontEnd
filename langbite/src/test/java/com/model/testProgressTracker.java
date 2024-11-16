package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class testProgressTracker {

    private ProgressTracker progressTracker;

    @Before
    public void setUp() {
        // Initialize ProgressTracker with sample totals
        progressTracker = new ProgressTracker(10, 20, 100);
    }

    // Test that the constructor initializes the fields correctly with integer inputs
    @Test
    public void testConstructorWithIntegers() {
        assertEquals(12, progressTracker.totalRules);
        assertEquals(20, progressTracker.totalConnectorWords);
        assertEquals(100, progressTracker.totalMostCommonWords);
        assertEquals(0, progressTracker.completedRules);
        assertEquals(0, progressTracker.masteredConnectorWords);
        assertEquals(0, progressTracker.masteredMostCommonWords);
    }

    // Test that the constructor initializes the fields correctly with a map input
    @Test
    public void testConstructorWithMap() {
        Map<String, Integer> progressData = Map.of(
            "totalRules", 15,
            "completedRules", 5,
            "totalQuestions", 50,
            "completedQuestions", 10,
            "totalConnectorWords", 30,
            "masteredConnectorWords", 15,
            "totalMostCommonWords", 80,
            "masteredMostCommonWords", 40
        );
        ProgressTracker trackerFromMap = new ProgressTracker(progressData);

        assertEquals(15, trackerFromMap.totalRules);
        assertEquals(5, trackerFromMap.completedRules);
        assertEquals(50, trackerFromMap.totalQuestions);
        assertEquals(10, trackerFromMap.completedQuestions);
        assertEquals(30, trackerFromMap.totalConnectorWords);
        assertEquals(15, trackerFromMap.masteredConnectorWords);
        assertEquals(80, trackerFromMap.totalMostCommonWords);
        assertEquals(40, trackerFromMap.masteredMostCommonWords);
    }

    // Test percentage calculations
    @Test
    public void testCompletionPercentages() {
        progressTracker.completedRules = 6;
        progressTracker.masteredConnectorWords = 10;
        progressTracker.masteredMostCommonWords = 50;

        assertEquals(50, progressTracker.getGrammarRulesCompletionPercentage());
        assertEquals(50, progressTracker.getConnectorWordsCompletionPercentage());
        assertEquals(50, progressTracker.getMostCommonWordsCompletionPercentage());
    }

    // Test incrementing methods for mastery counts
    @Test
    public void testIncrementMasteryCounts() {
        progressTracker.incrementMasteredConnectorWords();
        assertEquals(1, progressTracker.masteredConnectorWords);

        progressTracker.incrementMasteredMostCommonWords();
        assertEquals(1, progressTracker.masteredMostCommonWords);
    }

    // Test marking a rule as complete
    @Test
    public void testMarkRuleAsComplete() {
        progressTracker.markRuleAsComplete();
        assertEquals(1, progressTracker.completedRules);
    }

    // Test setting and tracking completed questions
    @Test
    public void testSetTotalQuestionsForRule() {
        progressTracker.setTotalQuestionsForRule(5);
        assertEquals(5, progressTracker.totalQuestions);
        assertEquals(0, progressTracker.getCompletedQuestions());
    }

    // Test incrementing completed questions
    @Test
    public void testIncrementCompletedQuestions() {
        progressTracker.setTotalQuestionsForRule(5);
        progressTracker.incrementCompletedQuestions();
        progressTracker.incrementCompletedQuestions();

        assertEquals(2, progressTracker.getCompletedQuestions());
    }

    // Test that completed questions cannot exceed the total number of questions
    @Test
    public void testCompletedQuestionsDoesNotExceedTotal() {
        progressTracker.setTotalQuestionsForRule(3);

        for (int i = 0; i < 5; i++) { // Attempting to increment beyond the limit
            progressTracker.incrementCompletedQuestions();
        }
        assertEquals(3, progressTracker.getCompletedQuestions());
    }

    // Test toProgressPercentageData method
    @Test
    public void testToProgressPercentageData() {
        progressTracker.completedRules = 6;
        progressTracker.masteredConnectorWords = 10;
        progressTracker.masteredMostCommonWords = 50;

        Map<String, Integer> percentages = progressTracker.toProgressPercentageData();
        assertEquals(50, (int) percentages.get("grammarRulesCompletionPercentage"));
        assertEquals(50, (int) percentages.get("connectorWordsCompletionPercentage"));
        assertEquals(50, (int) percentages.get("mostCommonWordsCompletionPercentage"));
    }

    // Test isRuleComplete when all questions are answered
    @Test
    public void testIsRuleComplete() {
        progressTracker.setTotalQuestionsForRule(3);
        
        progressTracker.incrementCompletedQuestions();
        progressTracker.incrementCompletedQuestions();
        progressTracker.incrementCompletedQuestions();

        assertTrue(progressTracker.isRuleComplete());
    }

    // Test isRuleComplete when not all questions are answered
    @Test
    public void testIsRuleNotComplete() {
        progressTracker.setTotalQuestionsForRule(3);

        progressTracker.incrementCompletedQuestions();
        progressTracker.incrementCompletedQuestions();

        assertFalse(progressTracker.isRuleComplete());
    }
}
