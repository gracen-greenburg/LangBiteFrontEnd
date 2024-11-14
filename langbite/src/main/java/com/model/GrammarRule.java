package com.model;

import java.util.List;

public class GrammarRule {
    private String ruleText;
    private String explanation;
    private String examples;
    private int attempts;
    private int correctAttempts;
    private List<GrammarQuestion> questions;

    public GrammarRule(String ruleText, String explanation, String examples, int attempts, int correctAttempts, List<GrammarQuestion> questions) {
        this.ruleText = ruleText;
        this.explanation = explanation;
        this.examples = examples;
        this.attempts = attempts;
        this.correctAttempts = correctAttempts;
        this.questions = questions;
    }

    public String getRuleText() { return ruleText; }
    public String getExplanation() { return explanation; }
    public String getExamples() { return examples; }
    public int getAttempts() { return attempts; }
    public int getCorrectAttempts() { return correctAttempts; }
    public List<GrammarQuestion> getQuestions() { return questions; }

    public void incrementAttempts() { attempts++; }
    public void incrementCorrectAttempts() { correctAttempts++; }
}
