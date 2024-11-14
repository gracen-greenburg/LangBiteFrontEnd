package com.model;

import java.util.Map;

public class ProgressTracker {
    int totalQuestions;
    int completedQuestions;
    int totalRules;
    int completedRules;
    int totalConnectorWords;
    int masteredConnectorWords;
    int totalMostCommonWords;
    int masteredMostCommonWords;

    public ProgressTracker(int totalRules, int totalConnectorWords, int totalMostCommonWords) {
        this.totalRules =  12;
        this.totalConnectorWords = totalConnectorWords;
        this.totalMostCommonWords = totalMostCommonWords;
        this.completedRules = 0;
        this.completedQuestions = 0;
        this.masteredConnectorWords = 0;
        this.masteredMostCommonWords = 0;
    }

    public ProgressTracker(Map<String, Integer> progressData) {
        this.totalRules = progressData.getOrDefault("totalRules", 0);
        this.completedRules = progressData.getOrDefault("completedRules", 0);
        this.totalQuestions = progressData.getOrDefault("totalQuestions", 0);
        this.completedQuestions = progressData.getOrDefault("completedQuestions", 0);
        this.totalConnectorWords = progressData.getOrDefault("totalConnectorWords", 0);
        this.masteredConnectorWords = progressData.getOrDefault("masteredConnectorWords", 0);
        this.totalMostCommonWords = progressData.getOrDefault("totalMostCommonWords", 0);
        this.masteredMostCommonWords = progressData.getOrDefault("masteredMostCommonWords", 0);
    }

    public Map<String, Integer> toProgressPercentageData() {
        return Map.of(
            "grammarRulesCompletionPercentage", getGrammarRulesCompletionPercentage(),
            "connectorWordsCompletionPercentage", getConnectorWordsCompletionPercentage(),
            "mostCommonWordsCompletionPercentage", getMostCommonWordsCompletionPercentage()
        );
    }

    public int getGrammarRulesCompletionPercentage() {
        return (totalRules == 0) ? 0 : (completedRules * 100) / totalRules;
    }

    public int getConnectorWordsCompletionPercentage() {
        return (totalConnectorWords == 0) ? 0 : (masteredConnectorWords * 100) / totalConnectorWords;
    }

    public int getMostCommonWordsCompletionPercentage() {
        return (totalMostCommonWords == 0) ? 0 : (masteredMostCommonWords * 100) / totalMostCommonWords;
    }

    public void incrementMasteredMostCommonWords() {
        masteredMostCommonWords++;
    }

    public void incrementMasteredConnectorWords() {
        masteredConnectorWords++;
    }

    public void markRuleAsComplete() {
        completedRules++;
    }

    public void displayMostCommonWordsProgress(){
        System.out.println("Hundred Most Common Words Completion: " + getMostCommonWordsCompletionPercentage() + "% (" + masteredMostCommonWords + "/" + totalMostCommonWords + " words mastered)");
    }

    public void displayConnectorWordsProgress() {
        System.out.println("Connector Words Completion: " + getConnectorWordsCompletionPercentage() + "% (" + masteredConnectorWords + "/" + totalConnectorWords + " words mastered)");
    }

    public void displayTotalGrammarProgress() {
        System.out.println("Grammar Rules Completion: " + getGrammarRulesCompletionPercentage() + "% (" + completedRules + "/" + totalRules + " rules complete)");
    }

    // Display method for all progress percentages
    public void displayProgress() {
        System.out.println("Grammar Rules Completion: " + getGrammarRulesCompletionPercentage() + "% (" + completedRules + "/" + totalRules + " rules complete)");
        System.out.println("Connector Words Completion: " + getConnectorWordsCompletionPercentage() + "% (" + masteredConnectorWords + "/" + totalConnectorWords + " words mastered)");
        System.out.println("Hundred Most Common Words Completion: " + getMostCommonWordsCompletionPercentage() + "% (" + masteredMostCommonWords + "/" + totalMostCommonWords + " words mastered)");
    }

    // Method to set the total number of questions for the current rule
    public void setTotalQuestionsForRule(int total) {
        this.totalQuestions = total;
        this.completedQuestions = 0; // Reset completed questions for the new rule
    }

    // Method to increment the count of completed questions
    public void incrementCompletedQuestions() {
        if (completedQuestions < totalQuestions) {
            completedQuestions++;
        }
    }

    public int getCompletedQuestions() {
        return completedQuestions;
    }
    
    public boolean isRuleComplete() {
        return completedQuestions >= totalQuestions;
    }
    
}
