package com.model;

import java.util.Scanner;
import org.json.simple.JSONObject;
import java.util.ArrayList;

public class LanguageModule {
    private JSONObject currentUser;
    ProgressTracker progressTracker;

    public LanguageModule(JSONObject currentUser) {
        this.currentUser = currentUser;
        JSONObject progress = (JSONObject) currentUser.get("progress");

        // Initialize ProgressTracker with percentage values directly
        int grammarRulesCompletion = ((Long) progress.getOrDefault("grammarRulesCompletionPercentage", 0L)).intValue();
        int connectorWordsCompletion = ((Long) progress.getOrDefault("connectorWordsCompletionPercentage", 0L)).intValue();
        int mostCommonWordsCompletion = ((Long) progress.getOrDefault("mostCommonWordsCompletionPercentage", 0L)).intValue();

        this.progressTracker = new ProgressTracker(
                grammarRulesCompletion,
                connectorWordsCompletion,
                mostCommonWordsCompletion
        );
    }

    public void startModule(Scanner scanner) {
        System.out.println("Welcome to the French Language Module.");
        JSONObject progress = (JSONObject) currentUser.get("progress");

        Long grammarRulesCompletion = (Long) progress.getOrDefault("grammarRulesCompletionPercentage", 0L);
        Long connectorWordsCompletion = (Long) progress.getOrDefault("connectorWordsCompletionPercentage", 0L);
        Long mostCommonWordsCompletion = (Long) progress.getOrDefault("mostCommonWordsCompletionPercentage", 0L);

        if (grammarRulesCompletion == 100 && connectorWordsCompletion == 100 && mostCommonWordsCompletion == 100) {
            System.out.println("You have completed all lessons, but you can review them. Would you like to 'review'?");
            String choice = scanner.nextLine();
            if ("review".equalsIgnoreCase(choice) || "yes".equalsIgnoreCase(choice)) {
                System.out.println("Reviewing the Hundred Most Common Words module...");
                new HundredMostCommonWords(currentUser).start(scanner);
            }
        } else if (grammarRulesCompletion < 100 && connectorWordsCompletion == 100 && mostCommonWordsCompletion == 100) {
            System.out.println("You have mastered most sections. Would you like to 'continue' with Grammar Lesson?");
            String choice = scanner.nextLine();
            if ("continue".equalsIgnoreCase(choice) || "yes".equalsIgnoreCase(choice)) {
                System.out.println("Continuing with Grammar Lesson...");
                ArrayList<GrammarRule> rules = DataLoader.loadGrammarRules();
                new GrammarLesson(rules, progressTracker, currentUser).start(scanner);
            }
        } else if (connectorWordsCompletion < 100 && mostCommonWordsCompletion == 100) {
            System.out.println("You are making progress. Would you like to 'continue' with Connector Words?");
            String choice = scanner.nextLine();
            if ("continue".equalsIgnoreCase(choice) || "yes".equalsIgnoreCase(choice)) {
                System.out.println("Continuing with Connector Words module...");
                new ConnectorWords(currentUser).start(scanner);
            }
        } else if (mostCommonWordsCompletion > 0) {
            System.out.println("You have made some progress. Would you like to 'continue' with the Hundred Most Common Words?");
            String choice = scanner.nextLine();
            if ("continue".equalsIgnoreCase(choice) || "yes".equalsIgnoreCase(choice)) {
                System.out.println("Continuing with Hundred Most Common Words module...");
                new HundredMostCommonWords(currentUser).start(scanner);
            }
        } else if (mostCommonWordsCompletion == 0) {
            System.out.println("You are just starting out! Would you like to 'start' the Hundred Most Common Words module?");
            String choice = scanner.nextLine();
            if ("start".equalsIgnoreCase(choice) || "yes".equalsIgnoreCase(choice)) {
                System.out.println("Starting the Hundred Most Common Words module...");
                new HundredMostCommonWords(currentUser).start(scanner);
            }
        } else {
            System.out.println("Invalid input. Returning to Language List.");
        }
    }
}
