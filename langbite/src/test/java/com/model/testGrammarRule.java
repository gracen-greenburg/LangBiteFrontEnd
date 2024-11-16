package com.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class testGrammarRule {

    private GrammarRule grammarRule;
    private String ruleText;
    private String explanation;
    private String examples;
    private int attempts;
    private int correctAttempts;
    private List<GrammarQuestion> questions;

    @Before
    public void setUp() {
        // Initialize test data
        ruleText = "Basic Grammar Rule";
        explanation = "This rule covers basic grammar.";
        examples = "Example: Subject + Verb + Object";
        attempts = 3;
        correctAttempts = 2;

        // Create sample questions
        GrammarQuestion question1 = new GrammarQuestion("Translate 'hello'", Arrays.asList("bonjour", "hola", "ciao"), 0, "input", "bonjour");
        GrammarQuestion question2 = new GrammarQuestion("Translate 'goodbye'", Arrays.asList("au revoir", "adios", "arrivederci"), 0, "input", "au revoir");
        questions = Arrays.asList(question1, question2);

        // Create GrammarRule instance with test data
        grammarRule = new GrammarRule(ruleText, explanation, examples, attempts, correctAttempts, questions);
    }

    // Test that the constructor initializes all fields correctly
    @Test
    public void testConstructor() {
        assertEquals(ruleText, grammarRule.getRuleText());
        assertEquals(explanation, grammarRule.getExplanation());
        assertEquals(examples, grammarRule.getExamples());
        assertEquals(attempts, grammarRule.getAttempts());
        assertEquals(correctAttempts, grammarRule.getCorrectAttempts());
        assertEquals(questions, grammarRule.getQuestions());
    }

    // Test the getRuleText method
    @Test
    public void testGetRuleText() {
        assertEquals("Basic Grammar Rule", grammarRule.getRuleText());
    }

    // Test the getExplanation method
    @Test
    public void testGetExplanation() {
        assertEquals("This rule covers basic grammar.", grammarRule.getExplanation());
    }

    // Test the getExamples method
    @Test
    public void testGetExamples() {
        assertEquals("Example: Subject + Verb + Object", grammarRule.getExamples());
    }

    // Test the getAttempts method
    @Test
    public void testGetAttempts() {
        assertEquals(3, grammarRule.getAttempts());
    }

    // Test the getCorrectAttempts method
    @Test
    public void testGetCorrectAttempts() {
        assertEquals(2, grammarRule.getCorrectAttempts());
    }

    // Test the getQuestions method
    @Test
    public void testGetQuestions() {
        assertEquals(questions, grammarRule.getQuestions());
    }

    // Test incrementing attempts
    @Test
    public void testIncrementAttempts() {
        grammarRule.incrementAttempts();
        assertEquals(4, grammarRule.getAttempts());
    }

    // Test incrementing correct attempts
    @Test
    public void testIncrementCorrectAttempts() {
        grammarRule.incrementCorrectAttempts();
        assertEquals(3, grammarRule.getCorrectAttempts());
    }
}
