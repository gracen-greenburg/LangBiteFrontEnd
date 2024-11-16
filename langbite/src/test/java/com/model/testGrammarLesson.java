package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class testGrammarLesson {

    private GrammarLesson grammarLesson;
    private ProgressTracker progressTracker;
    private JSONObject currentUser;

    @Before
    public void setUp() {
        // Initialize ProgressTracker with sample values for total rules, connector
        // words, and most common words
        progressTracker = new ProgressTracker(12, 10, 100); // replace these values as appropriate for your test

        // Set up dummy JSONObject for the current user
        currentUser = new JSONObject();
        currentUser.put("username", "testUser");

        // Initialize GrammarLesson with an empty list of rules for testing
        grammarLesson = new GrammarLesson(new ArrayList<>(), progressTracker, currentUser);
    }

    // Test that the constructor initializes the variables correctly
    @Test
    public void testConstructor() {
        ArrayList<GrammarRule> rules = new ArrayList<>();
        GrammarLesson lesson = new GrammarLesson(rules, progressTracker, currentUser);

        assertEquals(rules, lesson.rules);
        assertEquals(progressTracker, lesson.progressTracker);
        assertEquals(currentUser, lesson.currentUser);
    }

    // Test the formatForConsole method
    @Test
    public void testFormatForConsole() {
        String text = "<strong>Bold</strong> <em>Italic</em> <br> New Line";
        String formattedText = GrammarLesson.formatForConsole(text);

        assertEquals("\u001B[1mBold\u001B[0m _Italic_ \n New Line", formattedText);
    }

    // Test that a grammar rule with questions can be correctly added and accessed
    @Test
    public void testAddAndAccessGrammarRule() {
        // Create a sample question
        GrammarQuestion question = new GrammarQuestion("what is the plural of chat", List.of("chats", "chat", "chates"), 0,
                "input", "chats");

        // Create a rule with the question and add it to GrammarLesson
        GrammarRule rule = new GrammarRule("Plurals of nouns", "you add an s to the end of a noun to make it plural", "Examples: chat(s), garcon(s)", 0, 0,
                List.of(question));
        grammarLesson.rules.add(rule);

        assertEquals(1, grammarLesson.rules.size());
        assertEquals("Plurals of nouns", grammarLesson.rules.get(0).getRuleText());
        assertEquals("you add an s to the end of a noun to make it plural", grammarLesson.rules.get(0).getExplanation());
        assertEquals("chats", grammarLesson.rules.get(0).getQuestions().get(0).getExpectedAnswer());
    }

    // Test the incrementing of attempts and correct attempts in GrammarRule
    @Test
    public void testGrammarRuleAttempts() {
        // Create a rule with no questions for simplicity
        GrammarRule rule = new GrammarRule("Sample Rule", "Explanation", "Examples", 0, 0, new ArrayList<>());

        rule.incrementAttempts();
        rule.incrementCorrectAttempts();

        assertEquals(1, rule.getAttempts());
        assertEquals(1, rule.getCorrectAttempts());
    }

    // Test the quizUserOnRule functionality with a correct answer
    @Test
    public void testQuizUserOnRuleCorrectAnswer() {
        // Set up a Scanner with pre-determined input
        Scanner scanner = new Scanner("a\n");

        // Create a question with a correct answer
        GrammarQuestion question = new GrammarQuestion("What is the plural of boy (garcon)", List.of("garcons", "garcon", "garcones"), 0, "multiple-choice",
                "2");
        GrammarRule rule = new GrammarRule("Plurals", "Add an 's'", "Examples: chat(s), garcon(s)", 0, 0,
                List.of(question));

        grammarLesson.quizUserOnRule(rule, scanner);

        assertEquals(1, rule.getCorrectAttempts());
        assertEquals(1, rule.getAttempts());
    }

    // Test the saveProgress method by checking currentUser for saved data
    @Test
    public void testSaveProgress() {
        grammarLesson.saveProgress();

        // Check if the currentUser JSON contains progress data after saving
        assertTrue(currentUser.containsKey("progress"));
    }

    // Test that progress is updated in ProgressTracker
    @Test
    public void testProgressTrackerUpdate() {
        GrammarRule rule = new GrammarRule("Sample Rule", "Explanation", "Examples", 0, 0, new ArrayList<>());
        progressTracker.setTotalQuestionsForRule(5);

        // Simulate answering questions correctly
        for (int i = 0; i < 5; i++) {
            rule.incrementCorrectAttempts();
            progressTracker.incrementCompletedQuestions();
        }

        assertEquals(5, progressTracker.getCompletedQuestions());
        assertTrue(progressTracker.isRuleComplete());
    }
}
