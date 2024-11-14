package com.model;
import java.util.ArrayList;
import java.util.List;
/*
 * Quiz
 * 
 * @author LangBite Team
 */
public class Quiz {
    /*
     * Quiz represents a quiz, containing a title, list of questions, 
     * and the methods needed to manage the quiz
     * 
     * @param quizTitle String the name of the quiz
     * @param questions List<Question> the list of questions on the quiz
     * @param currentQuestionIndex int keeps track of where the user is on the quiz
     * @param totalScore int the final amount of answers the user got correct
     * @param isComplete boolean checks to see if the user is done with the quiz
     */
    private String quizTitle;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int totalScore;
    private boolean isComplete;

    /*
     * Class Quiz instantiates the variables and constructs a new Quiz
     */
    public Quiz(String quizTitle, List<Question> questions) {
        this.quizTitle = quizTitle;
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.totalScore = 0;
        this.isComplete = false;
    }

    /*
     * Class startQuiz starts the quiz and resets the question index
     * and sets the isComplete to false
     */
    public void startQuiz() {
        this.currentQuestionIndex = 0;
        this.isComplete = false;
    }

    /* 
    * Class endQuiz ends the quiz and marks it as complete
    */
    public void endQuiz() {
        this.isComplete = true;
    }

    /*
     * Class nextQuestion takes the user to the next question on 
     * the quiz
     */
    public Question nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
        }
        return questions.get(currentQuestionIndex);
    }

    /*
     * Class previousQuestion takes the user to the previous question
     * that they were on
     */
    public Question previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
        }
        return questions.get(currentQuestionIndex);
    }

    /*
     * Class calculateTotalScore calculates the user's final score
     * based on the answers they submitted during the quiz
     */
    public int calculateTotalScore() {
        totalScore = questions.stream()
                .mapToInt(Question::calculateScore)
                .sum();
        return totalScore;
    }

    /*
     * Class isQuizComplete checks to see if the quiz is complete
     */
    public boolean isQuizComplete() {
        return currentQuestionIndex >= questions.size() - 1;
    }

    /*
     * Class resetQuiz resets the quiz and clears all changes made 
     * to variables
     */
    public void resetQuiz() {
        this.currentQuestionIndex = 0;
        this.totalScore = 0;
        this.isComplete = false;
    }

    /*
     * Class displayQuizResults shows the user their results and final
     * score
     */
    // public String displayQuizResults() {
    //     calculateTotalScore();
    //     return "Quiz Complete! Your total score is: " + totalScore;
    // }

    /*
     * Class addQuiz those with admin access can add a quiz
     */
    public void addQuiz(Quiz quiz) {
        // logic to add a quiz
    }

    /*
     * Class getQuizList retrieves a list of quizzes that the user
     * can currently take
     */
    public List<Quiz> getQuizList() {
        return new ArrayList<>();
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public boolean isComplete() {
        return isComplete;
    }
}


