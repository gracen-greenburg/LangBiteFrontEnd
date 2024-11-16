package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class testQuiz {
    private List<Question> sampleQuestions;
    private Quiz quiz;

    @Before
    public void setUp() {
        // Initialize sampleQuestions with some Question objects
        sampleQuestions = new ArrayList<>();
        sampleQuestions.add(new Question("What is the word for dog in French?", "Chein", "MCQ"));
        sampleQuestions.add(new Question("What is birthday in French?", "anniversaire", "MCQ"));
        sampleQuestions.add(new Question("What does rogue translate to?", "red", "MCQ"));

        // Initialize a Quiz instance using the sampleQuestions
        quiz = new Quiz("Sample Quiz", sampleQuestions);
    }
    // Tests initialization of variables
    @Test
    public void testConstructorInitializesFieldsCorrectly() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the word for dog in French?", "Chein", "MCQ"));
        Quiz quiz = new Quiz("Animals Quiz", questions);

        assertEquals("Animals Quiz", quiz.getQuizTitle());
        assertEquals(questions, quiz.getQuestions());
        assertEquals(0, quiz.getCurrentQuestionIndex());
        assertEquals(0, quiz.getTotalScore());
        assertFalse(quiz.isComplete());
    }

    // Tests if starting the quiz resets the quiz to the beginning
    @Test
    public void testStartResets() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is birthday in French?", "anniversaire", "MCQ"));
        Quiz quiz = new Quiz("Holidays Quiz", questions);

        quiz.startQuiz();
        assertEquals(0, quiz.getCurrentQuestionIndex());
        assertFalse(quiz.isQuizComplete());
    }

    // Tests if the end of a quiz actually marks it complete
    @Test
    public void testEndQuizComplete() {
        Quiz quiz = new Quiz("Test Quiz", new ArrayList<>());
        quiz.endQuiz();
        assertTrue(quiz.isComplete());
    }

    // Tests if going to the next question increments the index
    @Test
    public void testNextQuestionCount() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", "Answer 1", "MCQ"));
        questions.add(new Question("Question 2", "Answer 2", "MCQ"));
        Quiz quiz = new Quiz("Test Quiz", questions);

        quiz.nextQuestion();
        assertEquals(1, quiz.getCurrentQuestionIndex());
    }

    // Tests if going to teh previous question decrements the index
    @Test
    public void testPreviousQuestionDecrementsIndex() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", "Answer 1", "MCQ"));
        questions.add(new Question("Question 2", "Answer 2", "MCQ"));
        Quiz quiz = new Quiz("Test Quiz", questions);

        quiz.nextQuestion();
        quiz.previousQuestion();
        assertEquals(0, quiz.getCurrentQuestionIndex());
    }

    @Test
    public void testCalculateTotalScore() {
        Question question1 = new Question("Question 1", "Answer 1", "MCQ");
        question1.submitAnswer("Answer 1"); // Correct 
        Question question2 = new Question("Question 2", "Answer 2", "MCQ");
        question2.submitAnswer("Wrong Answer"); // Incorrect 

        List<Question> questions = List.of(question1, question2);
        Quiz quiz = new Quiz("Test Quiz", questions);

        assertEquals(1, quiz.calculateTotalScore());
    }

    // Tests if the quiz is complete when the last question is reached
    @Test
    public void testIsQuizCompleteAtLastQuestion() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", "Answer 1", "MCQ"));
        Quiz quiz = new Quiz("Test Quiz", questions);

        quiz.nextQuestion();  // Moving to the last question
        assertTrue(quiz.isQuizComplete());
    }

    // Tests resetting the quiz and that everything goes back to 0
    @Test
    public void testResetQuiz() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", "Answer 1", "MCQ"));
        Quiz quiz = new Quiz("Test Quiz", questions);

        quiz.nextQuestion();
        quiz.resetQuiz();

        assertEquals(0, quiz.getCurrentQuestionIndex());
        assertEquals(0, quiz.getTotalScore());
        assertFalse(quiz.isComplete());
    }

    // Tests that next question cannot go out of bounds for amount of questions
    @Test
    public void testNextQuestionBounds() {
        Quiz quiz = new Quiz("Sample Quiz", sampleQuestions);
        quiz.startQuiz();
        for (int i = 0; i < sampleQuestions.size(); i++) {
            quiz.nextQuestion();
        }
        assertEquals(sampleQuestions.size() - 1, quiz.getCurrentQuestionIndex());
    }

    // Tests that previous question cannot below 0
    @Test
    public void testPreviousQuestionBounds() {
        Quiz quiz = new Quiz("Sample Quiz", sampleQuestions);
        quiz.startQuiz();
        quiz.previousQuestion();
        assertEquals(0, quiz.getCurrentQuestionIndex());
    }

    // Tests that the quiz will start reset
    @Test
    public void testStartQuiz() {
        Quiz quiz = new Quiz("Sample Quiz", sampleQuestions);
        quiz.startQuiz();
        assertEquals(0, quiz.getCurrentQuestionIndex());
        assertFalse(quiz.isComplete());
    }

    // Tests that ending the quiz marks it as complete
    @Test
    public void testEndQuiz() {
        Quiz quiz = new Quiz("Sample Quiz", sampleQuestions);
        quiz.endQuiz();
        assertTrue(quiz.isComplete());
    }

    // Tests that adding and getting a quiz both work
    @Test
    public void testAddAndGetQuiz() {
        Quiz quiz1 = new Quiz("Quiz 1", new ArrayList<>());
        Quiz quiz2 = new Quiz("Quiz 2", new ArrayList<>());

        List<Quiz> quizList = quiz1.getQuizList();
        quiz1.addQuiz(quiz2);

        assertTrue(quizList.contains(quiz2));
    }
}
