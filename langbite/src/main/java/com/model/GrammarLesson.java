package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;

public class GrammarLesson {
    ArrayList<GrammarRule> rules;
    ProgressTracker progressTracker;
    JSONObject currentUser;

    public GrammarLesson(ArrayList<GrammarRule> rules, ProgressTracker progressTracker, JSONObject currentUser) {
        this.rules = rules;
        this.progressTracker = progressTracker;
        this.currentUser = currentUser;
    }

    public void start(Scanner scanner) {
        for (GrammarRule rule : rules) {
            System.out.println(formatForConsole(rule.getRuleText()));
            System.out.println(formatForConsole(rule.getExplanation()));
            System.out.println(formatForConsole(rule.getExamples()));

            System.out.println("\nPress Enter when you're ready to practice.");
            scanner.nextLine();

            // Set total questions for the rule in progress tracker
            progressTracker.setTotalQuestionsForRule(rule.getQuestions().size());
            quizUserOnRule(rule, scanner);

            // If all questions are correct, mark rule as complete
            if (rule.getCorrectAttempts() >= rule.getQuestions().size()) {
                System.out.println("You've learned this rule!");
                progressTracker.markRuleAsComplete();
            }

            // Display overall grammar rule progress
            progressTracker.displayTotalGrammarProgress();

            // Save progress after each rule
            saveProgress();
        }

        System.out.println("Progress saved. Great job!");
    }

    static String formatForConsole(String text) {
        if (text == null) return "";
        text = text.replaceAll("(?i)<strong>(.*?)</strong>", "\u001B[1m$1\u001B[0m");
        text = text.replaceAll("(?i)<em>(.*?)</em>", "_$1_");
        text = text.replaceAll("(?i)<br>", "\n");
        return text;
    }

    void quizUserOnRule(GrammarRule rule, Scanner scanner) {
        List<GrammarQuestion> incorrectQuestions = new ArrayList<>(rule.getQuestions());

        while (!incorrectQuestions.isEmpty()) {
            List<GrammarQuestion> nextRound = new ArrayList<>();

            for (GrammarQuestion question : incorrectQuestions) {
                System.out.println("\n" + question.getText());

                if ("input".equals(question.getType())) {
                    System.out.print("Your answer: ");
                    String userInput = scanner.nextLine().trim();
                    if (userInput.equalsIgnoreCase(question.getExpectedAnswer())) {
                        System.out.println("Correct!");
                        rule.incrementCorrectAttempts();
                        progressTracker.incrementCompletedQuestions();
                    } else {
                        System.out.println("Incorrect. The correct answer is: " + question.getExpectedAnswer());
                        nextRound.add(question);
                    }
                } else {
                    char optionLabel = 'a';
                    for (String option : question.getOptions()) {
                        System.out.println(optionLabel + ") " + option);
                        optionLabel++;
                    }

                    String userInput = scanner.nextLine();
                    int correctAnswerIndex = question.getCorrectAnswer();
                    String correctOption = question.getOptions().get(correctAnswerIndex);

                    if (userInput.equalsIgnoreCase(String.valueOf((char) ('a' + correctAnswerIndex)))) {
                        System.out.println("Correct!");
                        rule.incrementCorrectAttempts();
                        progressTracker.incrementCompletedQuestions();
                    } else {
                        System.out.println("Incorrect. The correct answer was " + (char) ('a' + correctAnswerIndex) + ") " + correctOption);
                        nextRound.add(question);
                    }
                }
                rule.incrementAttempts();
                progressTracker.displayTotalGrammarProgress();
            }

            incorrectQuestions = nextRound;
            if (!incorrectQuestions.isEmpty()) {
                System.out.println("\nLet's try those incorrect questions again!");
            }
        }
        System.out.println("Great! You've answered all questions correctly for this rule.");
    }

    void saveProgress() {
        // Convert progress data to JSONObject before saving
        currentUser.put("progress", DataWriter.mapToJSONObject(progressTracker.toProgressPercentageData()));
        DataWriter.saveUser(currentUser);
    }
}
