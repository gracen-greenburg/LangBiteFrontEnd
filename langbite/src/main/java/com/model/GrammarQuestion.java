package com.model;

import java.util.List;

public class GrammarQuestion {
    private String text;
    private List<String> options;
    private int correctAnswer;
    private String type;
    private String expectedAnswer;

    public GrammarQuestion(String text, List<String> options, int correctAnswer, String type, String expectedAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.type = type;
        this.expectedAnswer = expectedAnswer;
    }

    public String getText() { return text; }
    public List<String> getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
    public String getType() { return type; }
    public String getExpectedAnswer() { return expectedAnswer; }
}

