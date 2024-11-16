package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class testLanguageModule {

    private LanguageModule languageModule;
    private JSONObject currentUser;

    @Before
    public void setUp() {
        // Create a dummy JSONObject for current user with progress data
        currentUser = new JSONObject();
        JSONObject progress = new JSONObject();
        progress.put("grammarRulesCompletionPercentage", 0L);
        progress.put("connectorWordsCompletionPercentage", 0L);
        progress.put("mostCommonWordsCompletionPercentage", 0L);
        currentUser.put("progress", progress);

        // Initialize LanguageModule with this user data
        languageModule = new LanguageModule(currentUser);
    }

    // Test that the LanguageModule constructor correctly initializes the progress tracker
    @Test
    public void testConstructorInitialization() {
        assertNotNull(languageModule);
        assertNotNull(languageModule.progressTracker);
    }

    // Test the startModule when user has completed all progress (100% completion in all categories)
    @Test
    public void testStartModule_AllComplete() {
        JSONObject progress = (JSONObject) currentUser.get("progress");
        progress.put("grammarRulesCompletionPercentage", 100L);
        progress.put("connectorWordsCompletionPercentage", 100L);
        progress.put("mostCommonWordsCompletionPercentage", 100L);

        // Simulate input for review
        Scanner scanner = new Scanner("review\n");
        languageModule.startModule(scanner);
    }

    // Test the startModule when only grammar is incomplete
    @Test
    public void testStartModule_GrammarIncomplete() {
        JSONObject progress = (JSONObject) currentUser.get("progress");
        progress.put("grammarRulesCompletionPercentage", 50L);
        progress.put("connectorWordsCompletionPercentage", 100L);
        progress.put("mostCommonWordsCompletionPercentage", 100L);

        // Simulate input for continue
        Scanner scanner = new Scanner("continue\n");
        languageModule.startModule(scanner);
    }

    // Test the startModule when connector words are incomplete
    @Test
    public void testStartModule_ConnectorWordsIncomplete() {
        JSONObject progress = (JSONObject) currentUser.get("progress");
        progress.put("grammarRulesCompletionPercentage", 100L);
        progress.put("connectorWordsCompletionPercentage", 50L);
        progress.put("mostCommonWordsCompletionPercentage", 100L);

        // Simulate input for continue
        Scanner scanner = new Scanner("continue\n");
        languageModule.startModule(scanner);
    }

    // Test the startModule when starting the Hundred Most Common Words module for the first time
    @Test
    public void testStartModule_StartingMostCommonWords() {
        JSONObject progress = (JSONObject) currentUser.get("progress");
        progress.put("grammarRulesCompletionPercentage", 0L);
        progress.put("connectorWordsCompletionPercentage", 0L);
        progress.put("mostCommonWordsCompletionPercentage", 0L);

        // Simulate input for start
        Scanner scanner = new Scanner("start\n");
        languageModule.startModule(scanner);
    }

    // Test the startModule with invalid input to check default message
    @Test
    public void testStartModule_InvalidInput() {
        JSONObject progress = (JSONObject) currentUser.get("progress");
        progress.put("grammarRulesCompletionPercentage", 0L);
        progress.put("connectorWordsCompletionPercentage", 0L);
        progress.put("mostCommonWordsCompletionPercentage", 0L);

        // Simulate input with an invalid choice
        Scanner scanner = new Scanner("invalid\n");
        languageModule.startModule(scanner);
    }
}
