package com.model;
/*
 * Class Lesson
 */
import java.util.List;
import java.util.Scanner;

import com.model.DragandDrop.DragAndDrop;

/**
 * The `Lesson` class represents a language learning lesson.
 * Each lesson contains vocabulary words, grammar rules, cultural notes,
 * and different types of questions such as multiple choice, fill-in-the-blank, and drag-and-drop.
 * 
 * 
 * @author LangBite Team
 */

public class Lesson {
    private String lessonName; 
    private int moduleNumber;
    private List<Word> vocabulary;
    private String grammar;
    private String culturalNotes;
    private List<MutipleChoice> mcQuestions;
    private List<FillinTheBlank> fillInTheBlankQuestions;
    private List<DragAndDrop> dragAndDropQuestions;
    /**
     * Constructs a Lesson.
     * 
     * @param lessonName The name of the lesson.
     * @param vocabulary The list of vocabulary words in the lesson.
     * @param grammar The grammar rules associated with the lesson.
     * @param culturalNotes The cultural notes included in the lesson.
     * @param moduleNumber The module number associated with this lesson.
     * @param mcQuestions A list of multiple choice questions.
     * @param fillInTheBlankQuestions A list of fill-in-the-blank questions.
     * @param dragAndDropQuestions A list of drag-and-drop questions.
     */

   

    public Lesson(String lessonName, List<Word> vocabulary, String grammar, String culturalNotes, int moduleNumber,
                  List<MutipleChoice> mcQuestions, List<FillinTheBlank> fillInTheBlankQuestions,
                  List<DragAndDrop> dragAndDropQuestions) {
        this.lessonName = lessonName;
        this.vocabulary = vocabulary;
        this.grammar = grammar;
        this.culturalNotes = culturalNotes;
        this.moduleNumber = moduleNumber;
        this.mcQuestions = mcQuestions;
        this.fillInTheBlankQuestions = fillInTheBlankQuestions;
        this.dragAndDropQuestions = dragAndDropQuestions;
    }

    /**
     * Displays the lesson content including vocabulary, grammar, and cultural notes.
     */
    public void teachLesson() {
        System.out.println("Welcome to " + lessonName);
        System.out.println("\nVocabulary:");
        for (Word word : vocabulary) {
            System.out.println("- " + word.getWord() + " (" + word.getMeaning() + ")");
        }
        System.out.println("\nGrammar: " + grammar);
        System.out.println("\nCultural Notes: " + culturalNotes);
    }

    /**
     * Conducts all the questions in the lesson, including multiple choice, fill-in-the-blank, 
     * and drag-and-drop questions, and calculates the number of correct answers.
     * 
     * @return The number of correct answers provided by the user.
     */
    public int conductLesson() {
        int correctAnswers = 0;

        // Multiple Choice Questions
        for (MutipleChoice mcQuestion : mcQuestions) {
            mcQuestion.display();
            System.out.print("Your answer (enter the option letter): ");
            char mcAnswer = new Scanner(System.in).next().toUpperCase().charAt(0);

            if (mcQuestion.submitAnswer(mcAnswer)) {
                System.out.println("Correct!");
                correctAnswers++;
            } else {
                System.out.println("Incorrect. The correct answer was " + mcQuestion.getCorrectAnswer() + ".");
            }
        }

        // Fill-in-the-Blank Questions
        for (FillinTheBlank fillInQuestion : fillInTheBlankQuestions) {
            fillInQuestion.display();
            for (int i = 0; i < fillInQuestion.getCorrectAnswers().size(); i++) {
                System.out.print("Enter your answer for blank " + (i + 1) + ": ");
                String userAnswer = new Scanner(System.in).next();
                fillInQuestion.submitAnswer(i, userAnswer);
            }

            if (fillInQuestion.checkAnswers()) {
                System.out.println("All answers are correct!");
                correctAnswers++;
            } else {
                System.out.println("Some answers are incorrect.");
            }
        }

        // Drag and Drop Questions
        for (DragAndDrop dragAndDrop : dragAndDropQuestions) {
            dragAndDrop.displayDraggableItems();
            dragAndDrop.displayTargets();
            Scanner scanner = new Scanner(System.in);

            for (String item : dragAndDrop.getDraggableItems()) {
                System.out.print("Where would you place '" + item + "'? Enter target name: ");
                String target = scanner.nextLine();
                dragAndDrop.dragAndDropItem(item, target);
            }

            if (dragAndDrop.submitAnswer()) {
                System.out.println("You matched all items correctly!");
                correctAnswers++;
            } else {
                System.out.println("Some matches were incorrect.");
            }
        }

        return correctAnswers;
    }

     // Getter for LessonName
    public String getLessonName() {
        return lessonName;
    }

    // Getter for moduleNumber
    public int getModuleNumber() {
        return moduleNumber;
    }

    // Getter for vocabulary
    public List<Word> getVocabulary() {
        return vocabulary;
    }

    // Getter for grammar
    public String getGrammar() {
        return grammar;
    }

    // Getter for cultural notes
    public String getCulturalNotes() {
        return culturalNotes;
    }

    
    // Getters for questions
    public List<MutipleChoice> getMcQuestions() {
        return mcQuestions;
    }

    public List<FillinTheBlank> getFillInTheBlankQuestions() {
        return fillInTheBlankQuestions;
    }

    public List<DragAndDrop> getDragAndDropQuestions() {
        return dragAndDropQuestions;
    }

   
    
}
