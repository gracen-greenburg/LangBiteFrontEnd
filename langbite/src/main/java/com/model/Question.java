package com.model;
/*
 * Question
 * 
 * @author LangBite Team
 */
public class Question {
    /*
     * Question is the format of how a question is dealt with in the 
     * system. Includes the question text itself, the correct answer, 
     * the user's answer, the score that the user got, and what type 
     * of question it is
     * 
     * @param questionText String the question as a string
     * @param correctAnswer String the correct answer to the question
     * @param userAnswer String the answer the user gave
     * @param score int the score the user got
     * @param questionType String what type of question it is
     */
    private String questionText;
    private String correctAnswer;
    private String userAnswer;
    private int score;
    private String questionType;

    /*
     * Class Question instantiates all of the variables
     */
    public Question(String questionText, String correctAnswer, String questionType) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.userAnswer = "";
        this.score = 0;
        this.questionType = questionType;
    }

    /*
     * Class getQuestionText retrieves the test of the question
     */
    public String getQuestionText() {
        return this.questionText;
    }

    /*
     * Class submitAnswer submits the answer that the user inputted
     */
    public void submitAnswer(String answer) {
        this.userAnswer = answer;
    }

    /*
     * Class checkAnswer checks if user's answer is correct
     */
    public boolean checkAnswer() {
        return this.userAnswer.trim().equalsIgnoreCase(this.correctAnswer.trim());
    }

    /*
     * Class calculateScore calculates and returns the score for 
     * the question, being 1 if correct and 0 if incorrect
     */
    public int calculateScore() {
        this.score = checkAnswer() ? 1 : 0;
        return this.score;
    }

    /*
     * Class resetQuestion resets the question, clearing the user's
     * answer and data
     */
    public void resetQuestion() {
        this.userAnswer = "";
        this.score = 0;
    }

    public String getUserAnswer() { 
        return userAnswer;
    }

    public int getScore() {
        return score;
    }
}


